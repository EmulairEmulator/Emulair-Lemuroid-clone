package com.bigbratan.emulair.fragments.settings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerCoresLibrary.PendingOperationsMonitor
import com.bigbratan.emulair.common.managerPreferences.SharedPreferencesHelper
import com.bigbratan.emulair.common.managerSaveSync.SaveSyncManager
import com.bigbratan.emulair.common.managerStorage.DirectoriesManager
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SaveSyncFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var directoriesManager: DirectoriesManager

    @Inject
    lateinit var saveSyncManager: SaveSyncManager
    private lateinit var saveSyncPreferences: SaveSyncPreferences

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

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore =
            SharedPreferencesHelper.getSharedPreferencesDataStore(requireContext())

        saveSyncPreferences = SaveSyncPreferences(saveSyncManager)
        setPreferencesFromResource(R.xml.preference_empty, rootKey)
        saveSyncPreferences.addSaveSyncPreferences(preferenceScreen)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (saveSyncPreferences.onPreferenceTreeClick(activity, preference))
            return true

        return super.onPreferenceTreeClick(preference)
    }

    override fun onResume() {
        super.onResume()
        saveSyncPreferences.updatePreferences(preferenceScreen, false)
        PendingOperationsMonitor(requireContext()).anySaveOperationInProgress().observe(this) {
            saveSyncPreferences.updatePreferences(preferenceScreen, it)
        }
    }

    @dagger.Module
    class Module
}
