package com.bigbratan.emulair.fragmentPauseMenuActions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activityPauseMenu.PauseMenuContract
import com.bigbratan.emulair.common.utils.preferences.DummyDataStore
import com.bigbratan.emulair.common.metadataRetrograde.SystemCoreConfig
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.AndroidSupportInjection

class PauseMenuFragment : PreferenceFragmentCompat() {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = DummyDataStore
        setPreferencesFromResource(R.xml.preference_pause_menu, rootKey)

        val audioEnabled = activity?.intent?.getBooleanExtra(
            PauseMenuContract.EXTRA_AUDIO_ENABLED,
            false
        ) ?: false

        PauseMenuPreferences.setupAudioOption(preferenceScreen, audioEnabled)

        val fastForwardSupported = activity?.intent?.getBooleanExtra(
            PauseMenuContract.EXTRA_FAST_FORWARD_SUPPORTED,
            false
        ) ?: false

        val fastForwardEnabled = activity?.intent?.getBooleanExtra(
            PauseMenuContract.EXTRA_FAST_FORWARD,
            false
        ) ?: false

        PauseMenuPreferences.setupFastForwardOption(
            preferenceScreen,
            fastForwardEnabled,
            fastForwardSupported
        )

        val systemCoreConfig = activity?.intent?.getSerializableExtra(
            PauseMenuContract.EXTRA_SYSTEM_CORE_CONFIG
        ) as SystemCoreConfig

        PauseMenuPreferences.setupSaveLoadOptions(preferenceScreen, systemCoreConfig)

        val numDisks = activity?.intent?.getIntExtra(PauseMenuContract.EXTRA_DISKS, 0) ?: 0
        val currentDisk = activity?.intent?.getIntExtra(PauseMenuContract.EXTRA_CURRENT_DISK, 0) ?: 0
        if (numDisks > 1) {
            PauseMenuPreferences.setupChangeDiskOption(activity, preferenceScreen, currentDisk, numDisks)
        }

        PauseMenuPreferences.setupCoreOptions(preferenceScreen, systemCoreConfig)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        if (PauseMenuPreferences.onPreferenceTreeClicked(activity, preference))
            return true

        when (preference?.key) {
            "pref_game_section_save" -> {
                findNavController().navigate(R.id.pausemenu_save)
            }
            "pref_game_section_load" -> {
                findNavController().navigate(R.id.pausemenu_load)
            }
            "pref_game_reset" -> handleRestartAction(activity, requireContext())
            "pref_game_quit" -> handleQuitAction(activity, requireContext())
            "pref_game_section_core_options" -> {
                findNavController().navigate(R.id.pausemenu_core_options)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun handleRestartAction(activity: Activity?, context: Context) {
        MaterialAlertDialogBuilder(context, R.style.AlertDialog)
            .setTitle(R.string.popup_title)
            .setMessage(R.string.pause_menu_restart_prompt)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                val resultIntent = Intent().apply {
                    putExtra(PauseMenuContract.RESULT_RESET, true)
                }
                setResultAndFinish(activity, resultIntent)
            }
            .setNegativeButton(R.string.button_cancel) { _, _ -> }
            .show()
    }

    private fun handleQuitAction(activity: Activity?, context: Context) {
        MaterialAlertDialogBuilder(context, R.style.AlertDialog)
            .setTitle(R.string.popup_title)
            .setMessage(R.string.pause_menu_quit_prompt)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                val resultIntent = Intent().apply {
                    putExtra(PauseMenuContract.RESULT_QUIT, true)
                }
                setResultAndFinish(activity, resultIntent)
            }
            .setNegativeButton(R.string.button_cancel) { _, _ -> }
            .show()
    }

    private fun setResultAndFinish(activity: Activity?, resultIntent: Intent) {
        activity?.setResult(Activity.RESULT_OK, resultIntent)
        activity?.finish()
    }

    @dagger.Module
    class Module
}
