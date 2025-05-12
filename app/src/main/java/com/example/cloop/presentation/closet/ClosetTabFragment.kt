package com.example.cloop.presentation.closet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cloop.databinding.FragmentClosetTabBinding
import com.example.cloop.presentation.closet.adapter.ClosetItemAdapter
import com.example.cloop.presentation.closet.viewmodel.ClosetViewModel

class ClosetTabFragment : Fragment() {

    private var _binding: FragmentClosetTabBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ClosetViewModel by activityViewModels()

    private lateinit var adapter: ClosetItemAdapter
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString("category")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClosetTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ClosetItemAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.allClothes.observe(viewLifecycleOwner) {
            val filtered = viewModel.getClothesByCategory(category ?: "")
            adapter.submitList(filtered)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(category: String): ClosetTabFragment {
            return ClosetTabFragment().apply {
                arguments = Bundle().apply { putString("category", category) }
            }
        }
    }
}

