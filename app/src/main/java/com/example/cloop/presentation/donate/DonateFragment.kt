package com.example.cloop.presentation.donate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cloop.databinding.FragmentDonateBinding

class DonateFragment : Fragment() {

    private var _binding: FragmentDonateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 임시 테스트로 !!!!!!!! caution 이미지 클릭 시 다이얼로그 띄움
        binding.ivCaution.setOnClickListener {
            DonateConfirmDialog().show(parentFragmentManager, "DonateConfirmDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}