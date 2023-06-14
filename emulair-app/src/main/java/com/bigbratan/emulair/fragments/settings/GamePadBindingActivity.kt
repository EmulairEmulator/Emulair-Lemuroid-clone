package com.bigbratan.emulair.fragments.settings

import android.os.Bundle
import android.view.KeyEvent
import com.bigbratan.emulair.common.activities.retrograde.RetrogradeActivity
import com.bigbratan.emulair.managers.input.InputBindingUpdater
import com.bigbratan.emulair.managers.input.InputDeviceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import javax.inject.Inject

class GamePadBindingActivity : RetrogradeActivity() {

    @Inject
    lateinit var inputDeviceManager: InputDeviceManager

    private lateinit var inputBindingUpdater: InputBindingUpdater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inputBindingUpdater = InputBindingUpdater(inputDeviceManager, intent)

        if (savedInstanceState == null) {
            // MaterialAlertDialogBuilder(this, R.style.AlertDialog)
            MaterialAlertDialogBuilder(this)
                .setTitle(inputBindingUpdater.getTitle(applicationContext))
                .setMessage(inputBindingUpdater.getMessage(applicationContext))
                .setOnKeyListener { _, _, event -> handleKeyEvent(event) }
                .setOnDismissListener { finish() }
                .show()
        }
    }

    private fun handleKeyEvent(event: KeyEvent): Boolean {
        val result = inputBindingUpdater.handleKeyEvent(event)

        if (event.action == KeyEvent.ACTION_UP && result) {
            finish()
        }

        return result
    }

    @dagger.Module
    abstract class Module
}
