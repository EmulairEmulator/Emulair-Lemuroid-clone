package com.bigbratan.emulair.fragments.settings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.managerPreferences.SharedPreferencesHelper
import com.bigbratan.emulair.managerSettings.SettingsInteractor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AdvancedFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

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
        setPreferencesFromResource(R.xml.preference_settings_advanced, rootKey)
        AdvancedPreferences.updateCachePreferences(preferenceScreen)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(R.string.pref_key_reset_settings) -> handleResetSettings()
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun handleResetSettings() {
        // MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
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

    @dagger.Module
    class Module
}
