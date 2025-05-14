package com.example.cloop.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cloop.R
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.FragmentLookBinding
import com.example.cloop.presentation.home.adapter.LookAdapter
import com.example.cloop.presentation.home.viewmodel.LookViewModel

class LookFragment : Fragment() {

    private var _binding: FragmentLookBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LookViewModel
    private lateinit var adapter: LookAdapter
    private val args: LookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLookBinding.inflate(inflater, container, false)

        val lookService = RetrofitClient.lookService
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LookViewModel(lookService) as T
            }
        })[LookViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }

        val selectedDate = args.date
        binding.tvDate.text = selectedDate

        adapter = LookAdapter()
        binding.rvLookClothes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLookClothes.adapter = adapter

        viewModel.fetchLooksByDate(requireContext(), selectedDate)

        viewModel.lookItems.observe(viewLifecycleOwner) { lookItems ->
            if (lookItems.isNotEmpty()) {
                val look = lookItems.first()

                Glide.with(requireContext())
                    .load(look.imageUrl)
                    .into(binding.ivCloth)

                // 옷 리스트를 어댑터에 넘김
                adapter.submitList(look.clothes)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
