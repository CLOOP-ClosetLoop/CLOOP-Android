package com.example.cloop.presentation.closet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.cloop.MainActivity
import com.example.cloop.R
import com.example.cloop.TokenManager
import com.example.cloop.databinding.FragmentClothRegister2Binding
import com.example.cloop.presentation.closet.viewmodel.ClothRegisterViewModel

class ClothRegisterFragment2 : Fragment() {

    private var _binding: FragmentClothRegister2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: ClothRegisterViewModel by activityViewModels()

    private lateinit var seasonButtons: List<TextView>
    private var selectedSeason: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCategory.setText(viewModel.category)

        seasonButtons = listOf(binding.btnSummer, binding.btnWinter)
        seasonButtons.forEach { button ->
            button.setOnClickListener {
                updateSeasonSelection(button)
                selectedSeason = button.text.toString()
            }
        }

        binding.btnNext.setOnClickListener {

            viewModel.clothName = binding.etName.text.toString()
            viewModel.purchasedAt = binding.etDate.text.toString()
            viewModel.brand = binding.brand.text.toString()
            viewModel.color = binding.etColor.text.toString()
            viewModel.season = selectedSeason

            val token = TokenManager.getAccessToken(requireContext())
            if (token.isNullOrEmpty()) {
                return@setOnClickListener
            }
            val bearerToken = "Bearer $token"
            viewModel.registerCloth(bearerToken) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Item added!", Toast.LENGTH_SHORT).show()

                    val navController = NavHostFragment.findNavController(
                        requireActivity().supportFragmentManager.findFragmentById(R.id.fcv_main)!!
                    )
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.fragment_home, inclusive = false)
                        .build()
                    navController.navigate(R.id.fragment_home, null, navOptions)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateSeasonSelection(selectedButton: TextView) {
        seasonButtons.forEach { button ->
            button.isSelected = (button == selectedButton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
