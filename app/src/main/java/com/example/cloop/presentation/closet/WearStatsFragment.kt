package com.example.cloop.presentation.closet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloop.databinding.FragmentWearStatsBinding
import com.example.cloop.presentation.closet.adapter.WearStatsAdapter
import com.example.cloop.presentation.closet.viewmodel.WearStatsViewModel
import com.example.cloop.presentation.closet.viewmodel.WearStatsViewModelFactory

class WearStatsFragment : Fragment() {


    private var _binding: FragmentWearStatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WearStatsViewModel
    private lateinit var adapter: WearStatsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWearStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            WearStatsViewModelFactory(requireContext().applicationContext)
        )[WearStatsViewModel::class.java]

        adapter = WearStatsAdapter()
        binding.rvStatsClothes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvStatsClothes.adapter = adapter

        viewModel.wearStats.observe(viewLifecycleOwner) { stats ->
            adapter.submitList(stats)
        }

        viewModel.fetchWearStats()


        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}