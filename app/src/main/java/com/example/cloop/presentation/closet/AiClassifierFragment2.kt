package com.example.cloop.presentation.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.cloop.R
import com.example.cloop.TokenManager
import com.example.cloop.databinding.FragmentAiClassifier2Binding
import com.example.cloop.presentation.closet.viewmodel.ClothRegisterViewModel

class AiClassifierFragment2 : Fragment() {

    private var _binding: FragmentAiClassifier2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: ClothRegisterViewModel by activityViewModels()
    private lateinit var seasonButtons: List<TextView>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiClassifier2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setText(viewModel.clothName)
        binding.etCategory.setText(viewModel.category)
        binding.etColor.setText(viewModel.color)

        seasonButtons = listOf(binding.btnSummer, binding.btnWinter)
        when (viewModel.season?.uppercase()) {
            "SUMMER" -> selectSeason(binding.btnSummer)
            "WINTER" -> selectSeason(binding.btnWinter)
        }

        seasonButtons.forEach { button ->
            button.setOnClickListener {
                selectSeason(button)
                viewModel.season = button.text.toString()
            }
        }


        binding.btnNext.setOnClickListener {

            viewModel.clothName = binding.etName.text.toString()
            viewModel.category = binding.etCategory.text.toString()
            viewModel.color = binding.etColor.text.toString()
            viewModel.purchasedAt = binding.etDate.text.toString()
            viewModel.brand = binding.brand.text.toString()

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

    private fun selectSeason(selected: TextView) {
        seasonButtons.forEach { it.isSelected = (it == selected) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}