package com.example.cloop.ui.closet.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cloop.ui.closet.BlankClosetTabFragment

class ClosetPagerAdapter(
    fragment: Fragment,
    private val categories: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = categories.size

    override fun createFragment(position: Int): Fragment {
        return BlankClosetTabFragment.newInstance(categories[position])
    }
}
