package com.bigbratan.emulair.fragments.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.main.MainActivity
import com.bigbratan.emulair.managers.coresLibrary.LibraryIndexScheduler
import com.bigbratan.emulair.common.managers.preferences.SharedPreferencesHelper
import com.bigbratan.emulair.common.managers.saveSync.SaveSyncManager
import com.bigbratan.emulair.common.managers.storage.DirectoriesManager
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.managers.settings.SettingsInteractor
import com.bigbratan.emulair.ui.CustomListPreference
import com.fredporciuncula.flow.preferences.FlowSharedPreferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    @Inject
    lateinit var directoriesManager: DirectoriesManager

    @Inject
    lateinit var saveSyncManager: SaveSyncManager

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore =
            SharedPreferencesHelper.getSharedPreferencesDataStore(requireContext())
        setPreferencesFromResource(R.xml.preference_settings, rootKey)

        findPreference<Preference>(getString(R.string.pref_key_open_save_sync_settings))?.apply {
            isVisible = saveSyncManager.isSupported()
        }

        findPreference<Preference>(getString(R.string.pref_key_enable_monet))?.apply {
            isVisible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
        }

        // TODO: edit this so it's similar to how other preferences are defined,
        //  or at least make sure that this implementation follows the rules
        val listPreference = findPreference<Preference>(getString(R.string.pref_key_theme)) as CustomListPreference?
        listPreference?.setOnPreferenceChangeListener { preference, _ ->
            if (preference is CustomListPreference) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                requireActivity().startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                requireActivity().finish()
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()

        val factory = SettingsViewModel.Factory(
            requireContext(),
            FlowSharedPreferences(
                SharedPreferencesHelper.getLegacySharedPreferences(requireContext())
            )
        )
        val settingsViewModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]
        preferenceScreen

        val currentDirectory: Preference? = findPreference(getString(R.string.pref_key_external_folder))
        val rescanPreference: Preference? = findPreference(getString(R.string.pref_key_rescan))
        val stopRescanPreference: Preference? = findPreference(getString(R.string.pref_key_stop_rescan))
        val displayBiosPreference: Preference? = findPreference(getString(R.string.pref_key_display_bios_info))
        val resetSettings: Preference? = findPreference(getString(R.string.pref_key_reset_settings))

        launchOnState(Lifecycle.State.RESUMED) {
            settingsViewModel.currentFolder
                .collect {
                    currentDirectory?.summary = getDisplayNameForFolderUri(Uri.parse(it))
                        ?: getString(R.string.settings_roms_directory_description)
                }
        }

        settingsViewModel.indexingInProgress.observe(this) {
            rescanPreference?.isEnabled = !it
            currentDirectory?.isEnabled = !it
            displayBiosPreference?.isEnabled = !it
            resetSettings?.isEnabled = !it
        }

        settingsViewModel.directoryScanInProgress.observe(this) {
            stopRescanPreference?.isVisible = it
            rescanPreference?.isVisible = !it
        }
    }

    private fun getDisplayNameForFolderUri(uri: Uri): String? {
        return runCatching {
            DocumentFile.fromTreeUri(requireContext(), uri)?.name
        }.getOrNull()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(R.string.pref_key_rescan) -> rescanLibrary()
            getString(R.string.pref_key_stop_rescan) -> stopRescanLibrary()
            getString(R.string.pref_key_external_folder) -> handleChangeExternalFolder()
            getString(R.string.pref_key_open_gamepad_settings) -> handleOpenGamePadSettings()
            getString(R.string.pref_key_open_save_sync_settings) -> handleDisplaySaveSync()
            getString(R.string.pref_key_open_cores_selection) -> handleDisplayCorePage()
            getString(R.string.pref_key_display_bios_info) -> handleDisplayBiosInfo()
            getString(R.string.pref_key_advanced_settings) -> handleAdvancedSettings()
            getString(R.string.pref_key_reset_settings) -> handleResetSettings()
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun handleAdvancedSettings() {
        findNavController().navigate(R.id.main_advanced)
    }

    private fun handleDisplayBiosInfo() {
        findNavController().navigate(R.id.main_bios)
    }

    private fun handleDisplayCorePage() {
        findNavController().navigate(R.id.main_cores_selection)
    }

    private fun handleDisplaySaveSync() {
        findNavController().navigate(R.id.main_save_sync)
    }

    private fun handleOpenGamePadSettings() {
        findNavController().navigate(R.id.main_gamepad)
    }

    private fun handleChangeExternalFolder() {
        settingsInteractor.changeLocalStorageFolder()
    }

    private fun handleResetSettings() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.popup_title)
            .setMessage(R.string.advanced_general_reset_popup_description)
            .setPositiveButton(R.string.button_ok) { _, _ ->
                settingsInteractor.resetAllSettings()
                reloadPreferences()
            }
            .setNegativeButton(R.string.button_cancel) { _, _ -> }
            .show()
    }

    private fun reloadPreferences() {
        preferenceScreen = null
        setPreferencesFromResource(R.xml.preference_settings, null)
    }

    private fun rescanLibrary() {
        context?.let { LibraryIndexScheduler.scheduleLibrarySync(it) }
    }

    private fun stopRescanLibrary() {
        context?.let { LibraryIndexScheduler.cancelLibrarySync(it) }
    }

    @dagger.Module
    class Module
}
