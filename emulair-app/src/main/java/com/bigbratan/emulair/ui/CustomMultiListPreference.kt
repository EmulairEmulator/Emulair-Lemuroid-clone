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

    override fun onClick() {
        val entryValues = entryValues ?: return
        val selectedItems = selectedItems

        val builder =
            MaterialAlertDialogBuilder(context).setMultiChoiceItems(
                entries,
                selectedItems
            ) { _, which, isChecked ->
                val value = entryValues[which].toString()
                if (isChecked) {
                    addSelectedItem(value)
                } else {
                    removeSelectedItem(value)
                }
            }
                .setPositiveButton(R.string.button_ok) { _, _ -> saveSelectedItems() }
                .setNegativeButton(R.string.button_cancel) { dialog, _ -> dialog.dismiss() }

        val dialog = builder.create()
        dialog.show()
    }

    override fun getSelectedItems(): BooleanArray {
        val entryValues = entryValues ?: return BooleanArray(0)
        val selectedItems = mutableListOf<Boolean>()
        val selectedValues = values
        for (element in entryValues) {
            selectedItems.add(selectedValues.contains(element))
        }
        return selectedItems.toBooleanArray()
    }

    private fun addSelectedItem(value: String) {
        val newValues = (values ?: emptyList()) + value
        values = newValues.toSet()
    }

    private fun removeSelectedItem(value: String) {
        val newValues = (values ?: emptyList()) - value
        values = newValues.toSet()
    }

    private fun saveSelectedItems() {
        val selectedValues = values.joinToString(",")
        persistString(selectedValues)
    }
}
