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
import java.io.File
import android.Manifest
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.cloop.TokenManager
import com.example.cloop.presentation.closet.viewmodel.ClothRegisterViewModel

class ClothRegisterFragment : Fragment() {

    private var _binding: FragmentClothRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClothRegisterViewModel by activityViewModels()

    private val clothService = RetrofitClient.clothService

    private var imageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val CAMERA_CODE = 1001

    private lateinit var categoryButtons: List<TextView>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothRegisterBinding.inflate(inflater, container, false)

        binding.btnSelectPhoto.setOnClickListener { showImagePickerDialog() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        categoryButtons = listOf(
            binding.btnTop,
            binding.btnBottom,
            binding.btnOuter,
            binding.btnShoes,
            binding.btnBag,
            binding.btnHat,
            binding.btnEtc
        )
        categoryButtons.forEach { button ->
            button.setOnClickListener {
                updateCategorySelection(button)
                viewModel.category = button.text.toString()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            if (imageUri == null || viewModel.category.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please upload a photo and select a category.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            findNavController().navigate(R.id.clothRegisterFragment2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_CODE && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            Toast.makeText(requireContext(), "Camera permission is required.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateCategorySelection(selectedButton: TextView) {
        categoryButtons.forEach { button ->
            button.isSelected = (button == selectedButton)
        }
    }


    private fun showImagePickerDialog() {
        val options = arrayOf("Take a Photo", "Choose from Gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Photo")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> checkGalleryPermissionAndOpenGallery()
                }
            }
            .show()
    }

    private fun checkGalleryPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), IMAGE_PICK_CODE)
        } else {
            openGallery()
        }
    }


    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
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
                uploadImage(it)
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
            null
        }
    }

    private fun uploadImage(uri: Uri) {
        val file = uriToTempFile(uri)
        if (file == null) {
            Toast.makeText(requireContext(), "Unable to process the image file.", Toast.LENGTH_SHORT).show()
            return
        }

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        val accessToken = TokenManager.getAccessToken(requireContext())
        if (accessToken == null) {
            return
        }
        val bearerToken = "Bearer $accessToken"

        lifecycleScope.launch {
            try {
                val response = clothService.uploadClothImage(bearerToken, multipart)
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.imageUrl
                    viewModel.imageUrl = imageUrl
                } else {
                    val errorMsg = response.errorBody()?.string()
                }
            } catch (e: Exception) {
                Log.e("ImageUpload", "An exception occurred: ${e.message}")
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}