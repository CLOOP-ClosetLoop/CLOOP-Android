package com.example.cloop.ui.donate

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cloop.R
import com.example.cloop.databinding.DialogDonateConfirmBinding

class DonateConfirmDialog : DialogFragment() {

    private var _binding: DialogDonateConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogDonateConfirmBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        // 취소 버튼
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        // 기부 버튼
        binding.btnDonate.setOnClickListener {
            dialog.dismiss()
            DonateSuccessDialog().show(parentFragmentManager, "DonateSuccessDialog")
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

