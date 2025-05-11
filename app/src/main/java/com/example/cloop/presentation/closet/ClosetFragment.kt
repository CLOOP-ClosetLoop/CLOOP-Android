package com.example.cloop.presentation.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cloop.R
import com.example.cloop.databinding.FragmentClosetBinding
import com.example.cloop.presentation.closet.adapter.ClosetPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ClosetFragment : Fragment() {

    private var _binding: FragmentClosetBinding? = null
    private val binding get() = _binding!!

    private val categories = listOf("상의", "하의", "아우터", "신발", "가방", "모자", "기타")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        // ViewPager 연결
        val adapter = ClosetPagerAdapter(this, categories)
        binding.viewPager.adapter = adapter

        // TabLayout 연결
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