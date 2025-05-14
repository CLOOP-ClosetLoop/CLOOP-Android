package com.example.cloop.presentation.home

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloop.R
import com.example.cloop.TokenManager
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.FragmentOutfitRegisterBinding
import com.example.cloop.presentation.closet.viewmodel.ClosetViewModel
import com.example.cloop.presentation.home.adapter.SelectedClothAdapter
import com.example.cloop.presentation.home.viewmodel.OutfitRegisterViewModel

class OutfitRegisterFragment : Fragment() {

    private var _binding: FragmentOutfitRegisterBinding? = null
    private val binding get() = _binding!!

    private val args: OutfitRegisterFragmentArgs by navArgs()
    private val viewModel: OutfitRegisterViewModel by activityViewModels()

    private val closetViewModel: ClosetViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val service = RetrofitClient.clothService
                return ClosetViewModel(service) as T
            }
        }
    }

    private lateinit var selectedClothAdapter: SelectedClothAdapter

    private var imageUri: Uri? = null
    private val IMAGE_PICK_CODE = 1000
    private val CAMERA_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOutfitRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedDate = args.selectedDate
        Log.d("selectedDate", selectedDate)

        val token = TokenManager.getAccessToken(requireContext())
        if (!token.isNullOrEmpty()) {
            closetViewModel.fetchClothes(token)
        }

        closetViewModel.allClothes.observe(viewLifecycleOwner) { clothes ->
            viewModel.setAllClothes(clothes)
        }

        binding.btnSelectPhoto.setOnClickListener {
            showImagePickerDialog()
        }


        binding.btnGoToCloset.setOnClickListener { findNavController().navigate(R.id.action_outfitRegister_to_closetSelect) }
        binding.tvSelectedClothes.setOnClickListener { findNavController().navigate(R.id.action_outfitRegister_to_closetSelect) }

        binding.tvDate.text = selectedDate


        viewModel.selectedClothList.observe(viewLifecycleOwner) { selectedClothes ->
            selectedClothAdapter = SelectedClothAdapter(selectedClothes)
            binding.rvSelectedClothes.apply {
                layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
                adapter = selectedClothAdapter
            }
        }


        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                Toast.makeText(requireContext(), "Outfit registered!", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.fragment_home, null,
                    NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
                )
                viewModel.registerResult.value = null
            }
        }


        binding.btnRegister.setOnClickListener {
            val imageUrl = viewModel.imageUrl.value
            val selectedIds = viewModel.selectedClothList.value?.map { it.clothId } ?: emptyList()

            if (!imageUrl.isNullOrBlank() && selectedIds.isNotEmpty()) {
                viewModel.registerLook(requireContext(), selectedIds, wornDate = selectedDate )
            } else {
                Toast.makeText(requireContext(), "Please select both a photo and clothes", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    imageUri = data?.data
                    imageUri?.let {
                        binding.ivCloth.setImageURI(it)
                        viewModel.setImageUri(it)
                    }
                }

                CAMERA_CODE -> {
                    imageUri?.let {
                        binding.ivCloth.setImageURI(it)
                        viewModel.setImageUri(it)
                    }
                }
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Choose from Gallery", "Take a Photo")
        AlertDialog.Builder(requireContext())
            .setTitle("Select Photo")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> pickImageFromGallery()
                    1 -> takePhotoFromCamera()
                }
            }.show()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun takePhotoFromCamera() {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "New Picture")
            put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        }
        imageUri = requireActivity().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }
        startActivityForResult(intent, CAMERA_CODE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}