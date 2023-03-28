package com.bigbratan.emulair.fragmentSettings

import android.os.Bundle
import android.view.KeyEvent
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.activityRetrograde.RetrogradeActivity
import com.bigbratan.emulair.managerInput.InputBindingUpdater
import com.bigbratan.emulair.managerInput.InputDeviceManager
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
