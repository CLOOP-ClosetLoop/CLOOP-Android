package com.example.cloop.presentation.closet

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.FragmentClothRegisterBinding
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class ClothRegisterFragment : Fragment() {

    private var _binding: FragmentClothRegisterBinding? = null
    private val binding get() = _binding!!

    private val clothService = RetrofitClient.clothService

    private var imageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val CAMERA_CODE = 1001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothRegisterBinding.inflate(inflater, container, false)

        //binding.btnSelectPhoto.setOnClickListener { showImagePickerDialog() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 다음 화면으로 이동
        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.clothRegisterFragment2)
        }
    }



//    private fun showImagePickerDialog() {
//        val options = arrayOf("카메라", "갤러리")
//        AlertDialog.Builder(requireContext())
//            .setTitle("사진 선택")
//            .setItems(options) { _, which ->
//                when (which) {
//                    0 -> openCamera()
//                    1 -> openGallery()
//                }
//            }
//            .show()
//    }
//
//    private fun openCamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val file = File.createTempFile("camera_", ".jpg", requireContext().cacheDir)
//        imageUri = FileProvider.getUriForFile(
//            requireContext(),
//            "${requireContext().packageName}.provider",
//            file
//        )
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//        startActivityForResult(intent, CAMERA_CODE)
//    }
//
//    private fun openGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
//    }
//
//
//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == AppCompatActivity.RESULT_OK) {
//            imageUri = when (requestCode) {
//                IMAGE_PICK_CODE -> data?.data
//                CAMERA_CODE -> imageUri
//                else -> null
//            }
//
//            imageUri?.let {
//                Glide.with(this).load(it).into(binding.ivCloth)
//                uploadImage(it)
//            }
//        }
//    }
//
//    private fun uploadImage(uri: Uri) {
//        val file = File(getRealPathFromUri(uri) ?: return)
//        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val multipart = MultipartBody.Part.createFormData("image", file.name, requestFile)
//
//        lifecycleScope.launch {
//            try {
//                val response = clothService.uploadClothImage(multipart)
//                if (response.isSuccessful) {
//                    val imageUrl = response.body()?.imageUrl
//                    Log.d("ImageUpload", "✅ 업로드 성공: $imageUrl")
//                    // 이후 imageUrl 저장 처리 or ViewModel 전달
//                } else {
//                    Log.e("ImageUpload", "❌ 업로드 실패: ${response.errorBody()?.string()}")
//                }
//            } catch (e: Exception) {
//                Log.e("ImageUpload", "❌ 오류 발생: ${e.message}")
//            }
//        }
//    }
//
//    private fun getRealPathFromUri(uri: Uri): String? {
//        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
//        return try {
//            cursor?.moveToFirst()
//            val idx = cursor?.getColumnIndex(MediaStore.Images.Media.DATA)
//            val result = idx?.let { cursor.getString(it) }
//            cursor?.close()
//            result
//        } catch (e: Exception) {
//            null
//        }
//    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}