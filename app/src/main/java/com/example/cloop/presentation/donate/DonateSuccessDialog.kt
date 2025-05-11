package com.example.cloop.presentation.donate

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.DialogFragment
import com.example.cloop.R

class DonateSuccessDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.dialog_donate_success, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(view)
            .create()

        // 2초 뒤 자동 dismiss
        Handler(Looper.getMainLooper()).postDelayed({
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }, 2000)

        return dialog
    }
}
