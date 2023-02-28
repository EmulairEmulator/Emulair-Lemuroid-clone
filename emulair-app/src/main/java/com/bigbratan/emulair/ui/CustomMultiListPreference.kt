package com.bigbratan.emulair.ui

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import androidx.preference.MultiSelectListPreference
import com.bigbratan.emulair.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomMultiListPreference : MultiSelectListPreference {
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?) : super(context)

    val checkedItems = BooleanArray(entryValues.size)

    override fun onClick() {
        val builder = MaterialAlertDialogBuilder(context, R.style.AlertDialog).setMultiChoiceItems(
            entries,
            checkedItems
        ) { dialog, which, isChecked ->
            checkedItems[which] = isChecked;
        }
            .setPositiveButton(R.string.button_ok) { dialog, _ -> dialog.dismiss() }
            .setNegativeButton(R.string.button_cancel) { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }
}
