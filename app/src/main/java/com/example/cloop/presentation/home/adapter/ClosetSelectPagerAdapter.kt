package com.example.cloop.presentation.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cloop.data.model.cloth.Cloth
import com.example.cloop.presentation.home.CategoryClothSelectFragment
import com.example.cloop.presentation.home.viewmodel.OutfitRegisterViewModel

class ClosetSelectPagerAdapter(
    fragment: Fragment,
    private val categories: List<String>,
    private val clothes: List<Cloth>,
    private val onSelectionChanged: (List<Cloth>) -> Unit // ✅ 람다로 바꿔줌
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val category = categories[position]
        val filteredClothes = clothes.filter { it.category == category }

        return CategoryClothSelectFragment.newInstance(category, filteredClothes).apply {
            this.onSelectionChanged = onSelectionChanged // ✅ 외부에서 받은 람다 할당
        }
    }
}

