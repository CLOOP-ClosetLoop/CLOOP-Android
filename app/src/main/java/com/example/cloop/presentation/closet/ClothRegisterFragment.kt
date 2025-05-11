package com.example.cloop.presentation.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cloop.R
import com.example.cloop.databinding.FragmentClothRegisterBinding

class ClothRegisterFragment : Fragment() {

    private var _binding: FragmentClothRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClothRegisterBinding.inflate(inflater, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}