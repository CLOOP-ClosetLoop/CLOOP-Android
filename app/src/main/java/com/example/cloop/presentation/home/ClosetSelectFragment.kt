package com.example.cloop.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloop.R
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.databinding.FragmentClosetSelectBinding
import com.example.cloop.presentation.closet.adapter.ClosetItemAdapter
import com.example.cloop.presentation.home.adapter.ClosetSelectPagerAdapter
import com.example.cloop.presentation.home.adapter.ClothSelectAdapter
import com.example.cloop.presentation.home.viewmodel.OutfitRegisterViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ClosetSelectFragment : Fragment() {

    private var _binding: FragmentClosetSelectBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OutfitRegisterViewModel by activityViewModels()

    private lateinit var pagerAdapter: ClosetSelectPagerAdapter
    private val categories = listOf("TOP", "BOTTOM", "OUTER", "SHOES", "BAG", "HAT", "ETC")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosetSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clothes = viewModel.allClothes.value ?: emptyList()

        pagerAdapter = ClosetSelectPagerAdapter(
            fragment = this,
            categories = categories,
            clothes = clothes
        ) { selected ->
            viewModel.selectedClothList.value = selected
        }


        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = categories[position]
        }.attach()

        binding.btnNext.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

