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

        binding.llRegister.setOnClickListener {
            showRegisterClothDialog()
        }

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
                val dateStr = date.date.toString()
                val action = HomeFragmentDirections
                    .actionFragmentHomeToFragmentOutfitRegister(dateStr)
                findNavController().navigate(action)
            } ?: run {
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
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

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnRegister.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.clothRegisterFragment)
        }

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