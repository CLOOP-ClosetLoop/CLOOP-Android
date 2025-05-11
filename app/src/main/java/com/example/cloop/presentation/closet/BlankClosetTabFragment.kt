package com.example.cloop.presentation.closet

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

// 일단 임의로!!!!!!! 나중에 수정 필요

class BlankClosetTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val textView = TextView(context)
        textView.text = "카테고리 탭"
        textView.gravity = Gravity.CENTER
        return textView
    }

    companion object {
        fun newInstance(category: String) = BlankClosetTabFragment()
    }
}
