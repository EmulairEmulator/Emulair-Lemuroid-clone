package com.bigbratan.emulair.utils.android

import android.app.Activity
import com.bigbratan.emulair.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.displayErrorDialog(messageId: Int, actionLabelId: Int, action: () -> Unit) {
    displayErrorDialog(resources.getString(messageId), resources.getString(actionLabelId), action)
}

fun Activity.displayErrorDialog(message: String, actionLabel: String, action: () -> Unit) {
    MaterialAlertDialogBuilder(this, R.style.AlertDialog)
        .setMessage(message)
        .setPositiveButton(actionLabel) { _, _ -> action() }
        .setCancelable(false)
        .show()
}
