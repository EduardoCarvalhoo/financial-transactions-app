package com.example.desafiophi.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.desafiophi.R

fun Activity.showAlertDialog(message: String, positiveButtonAction: (() -> Unit)? = null) {
    AlertDialog.Builder(this)
        .setTitle(getString(R.string.error_dialog_title))
        .setMessage(message)
        .setPositiveButton(getString(R.string.error_dialog_positive_button_message)) { _, _ ->
            positiveButtonAction?.invoke()
        }
        .setNegativeButton(getString(R.string.error_dialog_negative_button_message)) { _, _ -> }
        .show()
}