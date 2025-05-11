package com.example.cloop.presentation.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.cloop.databinding.FragmentClothRegister2Binding

class ClothRegisterFragment2 : Fragment() {

    private var _binding: FragmentClothRegister2Binding? = null
    private val binding get() = _binding!!

    private lateinit var categoryButtons: List<TextView>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothRegister2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 카테고리 버튼 리스트 정의
        categoryButtons = listOf(
            binding.btnSummer,
            binding.btnWinter
        )
        // 클릭 리스너 공통 처리
        categoryButtons.forEach { button ->
            button.setOnClickListener {
                updateCategorySelection(button)
            }
        }

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateCategorySelection(selectedButton: TextView) {
        categoryButtons.forEach { button ->
            button.isSelected = (button == selectedButton)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}