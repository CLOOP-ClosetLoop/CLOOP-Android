package com.example.cloop.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cloop.R
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.databinding.FragmentCategoryClothSelectBinding
import com.example.cloop.presentation.home.adapter.ClothSelectAdapter
import com.example.cloop.presentation.home.viewmodel.OutfitRegisterViewModel

class CategoryClothSelectFragment : Fragment() {

    private var _binding: FragmentCategoryClothSelectBinding? = null
    private val binding get() = _binding!!

    private val outfitRegisterViewModel: OutfitRegisterViewModel by activityViewModels()

    private lateinit var adapter: ClothSelectAdapter
    private var category: String? = null
    private var clothes: List<Cloth> = emptyList()

    // ✅ 외부에서 설정 가능하도록 public var 선언
    var onSelectionChanged: ((List<Cloth>) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
            clothes = it.getParcelableArrayList(ARG_CLOTHES) ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryClothSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = ClothSelectAdapter(clothes, outfitRegisterViewModel)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        private const val ARG_CATEGORY = "category"
        private const val ARG_CLOTHES = "clothes"

        fun newInstance(category: String, clothes: List<Cloth>): CategoryClothSelectFragment {
            return CategoryClothSelectFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                    putParcelableArrayList(ARG_CLOTHES, ArrayList(clothes))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
