package com.example.cloop.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.cloop.R
import com.example.cloop.TokenManager
import com.example.cloop.data.remote.RetrofitClient
import com.example.cloop.databinding.DialogRegisterClothBinding
import com.example.cloop.databinding.FragmentHomeBinding
import com.example.cloop.presentation.home.calender.DotDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: CalendarDay? = null

    private val lookDates = mutableSetOf<CalendarDay>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llRegister.setOnClickListener { showRegisterClothDialog() }

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

        // ✅ API 호출해서 착장 있는 날짜 가져오기
        fetchLookDates()

        // ✅ 날짜 선택
        calendarView.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                selectedDate = date

                if (lookDates.contains(date)) {
                    // 🔁 착장 있는 날짜 → LookFragment로 이동
                    val dateStr = date.date.toString()
                    val action = HomeFragmentDirections.actionFragmentHomeToLookFragment(dateStr)
                    findNavController().navigate(action)
                }
            }
        }

        // ➕ 플로팅 버튼 → 착장 등록은 점 없는 날짜만 가능
        binding.btnPlus.setOnClickListener {
            selectedDate?.let { date ->
                if (lookDates.contains(date)) {
                    Toast.makeText(requireContext(), "이미 착장이 등록된 날짜입니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val dateStr = date.date.toString()
                    val action = HomeFragmentDirections.actionFragmentHomeToFragmentOutfitRegister(dateStr)
                    findNavController().navigate(action)
                }
            } ?: Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
        }

    }



    private fun fetchLookDates() {
        val token = TokenManager.getAccessToken(requireContext()) ?: return

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.lookService.getLookDates("Bearer $token")
                if (response.isSuccessful) {
                    val dateList = response.body().orEmpty()
                    lookDates.clear()
                    lookDates.addAll(dateList.map {
                        val localDate = org.threeten.bp.LocalDate.parse(it)
                        CalendarDay.from(localDate)
                    })

                    binding.calenderView.addDecorator(DotDecorator(lookDates))
                }
            } catch (e: Exception) {
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