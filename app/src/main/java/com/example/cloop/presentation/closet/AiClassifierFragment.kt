package com.example.cloop.presentation.closet

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.cloop.R
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cloop.TokenManager
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.FragmentAiClassifierBinding
import com.example.cloop.presentation.closet.viewmodel.ClothRegisterViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import android.Manifest

class AiClassifierFragment : Fragment() {

    private var _binding: FragmentAiClassifierBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClothRegisterViewModel by activityViewModels()
    private val clothService = RetrofitClient.clothService

    private var imageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val CAMERA_CODE = 1001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiClassifierBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSelectPhoto.setOnClickListener {
            showImagePickerDialog()
        }

        // 분류하기 버튼 눌렀을 때
        binding.btnNext.setOnClickListener {
            val token = TokenManager.getAccessToken(requireContext())
            if (token.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.classifyClothWithAI(token) { success ->
                if (success) {
                    findNavController().navigate(R.id.aiClassifierFragment2)
                } else {
                    Toast.makeText(requireContext(), "AI 분류 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // 뒤로가기
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    // 권한 처리 결과
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_CODE && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("카메라", "갤러리")
        AlertDialog.Builder(requireContext())
            .setTitle("사진 선택")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }.show()
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_CODE)
            return
        }

        val file = File.createTempFile("camera_", ".jpg", requireContext().cacheDir)
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
        startActivityForResult(intent, CAMERA_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            imageUri = when (requestCode) {
                IMAGE_PICK_CODE -> data?.data
                CAMERA_CODE -> imageUri
                else -> null
            }

            imageUri?.let {
                Glide.with(this).load(it).into(binding.ivCloth)
                uploadImageToServer(it)
            }
        }
    }

    private fun uriToTempFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = File.createTempFile("upload_", ".jpg", requireContext().cacheDir)
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            file
        } catch (e: Exception) {
            Log.e("AiClassifier", "파일 변환 실패: ${e.message}")
            null
        }
    }

    private fun uploadImageToServer(uri: Uri) {
        val file = uriToTempFile(uri) ?: return
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val token = TokenManager.getAccessToken(requireContext()) ?: return
        val bearerToken = "Bearer $token"

        lifecycleScope.launch {
            try {
                val response = clothService.uploadClothImage(bearerToken, multipart)
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.imageUrl
                    viewModel.imageUrl = imageUrl
                    Log.d("AiClassifier", "이미지 업로드 성공: $imageUrl")
                    Toast.makeText(requireContext(), "업로드 성공!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("AiClassifier", "서버 오류: ${response.code()}")
                    Toast.makeText(requireContext(), "업로드 실패", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("AiClassifier", "예외: ${e.message}")
                Toast.makeText(requireContext(), "예외 발생", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}