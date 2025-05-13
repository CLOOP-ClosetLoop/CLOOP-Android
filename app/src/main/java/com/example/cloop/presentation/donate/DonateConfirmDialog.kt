package com.example.cloop.presentation.donate

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.cloop.TokenManager
import com.example.cloop.databinding.DialogDonateConfirmBinding
import com.example.cloop.presentation.donate.viewmodel.DonateViewModel

class DonateConfirmDialog(
    private val clothId: Int,
    private val viewModel: DonateViewModel
) : DialogFragment() {

    private var _binding: DialogDonateConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDonateConfirmBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        // 취소
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // 기부
        binding.btnDonate.setOnClickListener {
            val token = TokenManager.getAccessToken(requireContext()) ?: ""
            viewModel.confirmDonation(token, clothId) { success ->
                dialog.dismiss()
                if (success) {
                    viewModel.removeClothItem(clothId)

                    DonateSuccessDialog().show(parentFragmentManager, "DonateSuccessDialog")
                } else {
                    Toast.makeText(requireContext(), "기부 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

