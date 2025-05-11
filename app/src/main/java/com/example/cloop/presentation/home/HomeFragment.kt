package com.example.cloop.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.cloop.R
import com.example.cloop.databinding.DialogRegisterClothBinding
import com.example.cloop.databinding.FragmentHomeBinding
import com.prolificinteractive.materialcalendarview.CalendarDay

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: CalendarDay? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // '새 옷 등록하기' 클릭 시 다이얼로그 띄움
        binding.llRegister.setOnClickListener {
            showRegisterClothDialog()
        }
        // '옷장 보기' 클릭 시 ClosetFragment 로 이동
        binding.llCloset.setOnClickListener {

            val navController = NavHostFragment.findNavController(
                requireActivity().supportFragmentManager.findFragmentById(R.id.fcv_main)!!
            )
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragment_home, false)
                .build()
            navController.navigate(R.id.fragment_closet, null, navOptions)

        }

        val calendarView = binding.calenderView

        // 날짜 선택하면 저장
        calendarView.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                selectedDate = date
            }
        }

        // 플러스 버튼 클릭 시 날짜 넘기며 이동
        binding.btnPlus.setOnClickListener {
            selectedDate?.let { date ->
                val dateStr = date.date.toString()  // LocalDate → "2025-04-23"
                val action = HomeFragmentDirections
                    .actionFragmentHomeToFragmentOutfitRegister(dateStr)
                findNavController().navigate(action)
            } ?: run {
                Toast.makeText(requireContext(), "날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showRegisterClothDialog() {
        val dialogBinding = DialogRegisterClothBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        // 닫기 버튼
        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        // 직접등록 버튼 클릭 시
        dialogBinding.btnRegister.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.clothRegisterFragment)
        }
        // AI 분류 버튼 클릭 시
        dialogBinding.btnAi.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.aiClassifierFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}