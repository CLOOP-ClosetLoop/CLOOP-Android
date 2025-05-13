package com.example.cloop.presentation.donate

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloop.TokenManager
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.data.repository.DonateRepository
import com.example.cloop.databinding.FragmentDonateBinding
import com.example.cloop.presentation.donate.adapter.DonateAdapter
import com.example.cloop.presentation.donate.viewmodel.DonateViewModel
import com.example.cloop.presentation.donate.viewmodel.DonateViewModelFactory

class DonateFragment : Fragment() {

    private var _binding: FragmentDonateBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DonateViewModel
    private lateinit var adapter: DonateAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DonateAdapter { clothItem ->
            DonateConfirmDialog(
                clothId = clothItem.clothId,
                viewModel = viewModel
            ).show(parentFragmentManager, "DonateConfirmDialog")
        }

        binding.rvInactiveClothes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvInactiveClothes.adapter = adapter

        val repository = DonateRepository(RetrofitClient.donateService)
        val factory = DonateViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DonateViewModel::class.java]

        viewModel.donationClothes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val token = TokenManager.getAccessToken(requireContext()) ?: ""
        viewModel.fetchDonationClothes(token)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}