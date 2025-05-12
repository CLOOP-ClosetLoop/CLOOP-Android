package com.example.cloop.presentation.closet.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cloop.presentation.closet.ClosetTabFragment

class ClosetPagerAdapter(
    fragment: Fragment,
    private val categories: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = categories.size

    override fun createFragment(position: Int): Fragment {
        return ClosetTabFragment.newInstance(categories[position])
    }
}
