package com.example.cloop.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.cloop.databinding.FragmentOutfitRegisterBinding

class OutfitRegisterFragment : Fragment() {

    private var _binding: FragmentOutfitRegisterBinding? = null
    private val binding get() = _binding!!

    private val args: OutfitRegisterFragmentArgs by navArgs()

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
        Log.d("받은날짜", selectedDate)  // ex: "2025-04-23"

        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}