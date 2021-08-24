package com.bignerdranch.android.otus

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class CustomDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setView(R.layout.dialog_custom)
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .setPositiveButton(R.string.exit) { _, _ ->
                    activity?.finish()
                }
                .create()
    }
}