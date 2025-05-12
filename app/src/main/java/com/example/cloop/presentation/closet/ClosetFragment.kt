package com.example.cloop.presentation.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cloop.R
import com.example.cloop.TokenManager
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.FragmentClosetBinding
import com.example.cloop.presentation.closet.adapter.ClosetPagerAdapter
import com.example.cloop.presentation.closet.viewmodel.ClosetViewModel
import com.example.cloop.presentation.closet.viewmodel.ClosetViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class ClosetFragment : Fragment() {

    private var _binding: FragmentClosetBinding? = null
    private val binding get() = _binding!!

    private val categories = listOf("TOP", "BOTTOM", "OUTER", "SHOES", "BAG", "HAT", "ETC")

    private val viewModel: ClosetViewModel by lazy {
        val factory = ClosetViewModelFactory(RetrofitClient.clothService)
        ViewModelProvider(requireActivity(), factory)[ClosetViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 토큰 불러오기
        val accessToken = TokenManager.getAccessToken(requireContext())

        if (!accessToken.isNullOrEmpty()) {
            viewModel.fetchClothes(accessToken)
        } else {
//            Toast.makeText(requireContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.loginFragment) // 예시
            return
        }

        val adapter = ClosetPagerAdapter(this, categories)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = categories[position]
        }.attach()

        binding.llInactiveCloset.setOnClickListener {
            findNavController().navigate(R.id.inactiveClosetFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
