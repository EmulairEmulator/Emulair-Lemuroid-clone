package com.bigbratan.emulair.fragments.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.preference.*
import com.bigbratan.emulair.ui.CustomMultiListPreference
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerSaveSync.SaveSyncWork
import com.bigbratan.emulair.common.metadata.retrograde.CoreID
import com.bigbratan.emulair.common.metadata.retrograde.GameSystem
import com.bigbratan.emulair.common.managerSaveSync.SaveSyncManager

class SaveSyncPreferences(private val saveSyncManager: SaveSyncManager) {

    fun addSaveSyncPreferences(preferenceScreen: PreferenceScreen) {
        val context = preferenceScreen.context

        val gdrive = createCategory(context, context.getString(R.string.save_sync_category_gdrive))
        gdrive.layoutResource = R.layout.layout_preference_universal_category_top
        preferenceScreen.addPreference(gdrive)

        Preference(context).apply {
            key = keyConfigure(context)
            layoutResource = R.layout.layout_preference_main_list_block_middle
            preferenceScreen.addPreference(this)
        }

        val synchronization = createCategory(context, context.getString(R.string.save_sync_category_synchronization))
        synchronization.layoutResource = R.layout.layout_preference_universal_category_middle
        preferenceScreen.addPreference(synchronization)

        SwitchPreference(context).apply {
            key = keySyncEnabled(context)
            layoutResource = R.layout.layout_preference_main_switch_top
            preferenceScreen.addPreference(this)
        }

        CustomMultiListPreference(context).apply {
        // MultiSelectListPreference(context).apply {
            key = keySyncCores(context)
            layoutResource = R.layout.layout_preference_main_multilist_middle
            preferenceScreen.addPreference(this)
        }

        SwitchPreference(context).apply {
            key = keyAutoSync(context)
            layoutResource = R.layout.layout_preference_main_switch_middle
            preferenceScreen.addPreference(this)
        }

        Preference(context).apply {
            key = keyForceSync(context)
            layoutResource = R.layout.layout_preference_main_simple_bottom
            preferenceScreen.addPreference(this)
        }

        updatePreferences(preferenceScreen, false)
    }

    fun updatePreferences(preferenceScreen: PreferenceScreen, syncInProgress: Boolean) {
        val context = preferenceScreen.context

        preferenceScreen.findPreference<Preference>(keyConfigure(context))?.apply {
            title = context.getString(
                R.string.save_sync_gdrive_configure_title,
                saveSyncManager.getProvider()
            )
            isIconSpaceReserved = false
            isEnabled = !syncInProgress
            summary = saveSyncManager.getConfigInfo()
        }

        preferenceScreen.findPreference<Preference>(keySyncEnabled(context))?.apply {
            title = context.getString(R.string.save_sync_synchronization_saves_title)
            summary = context.getString(
                R.string.save_sync_synchronization_saves_description,
                saveSyncManager.computeSavesSpace()
            )
            isEnabled = saveSyncManager.isConfigured() && !syncInProgress
            isIconSpaceReserved = false
        }

        preferenceScreen.findPreference<Preference>(keyAutoSync(context))?.apply {
            title = context.getString(R.string.save_sync_synchronization_auto_title)
            isEnabled = saveSyncManager.isConfigured() && !syncInProgress
            summary = context.getString(R.string.save_sync_synchronization_auto_description)
            dependency = keySyncEnabled(context)
            isIconSpaceReserved = false
        }

        preferenceScreen.findPreference<Preference>(keyForceSync(context))?.apply {
            title = context.getString(R.string.save_sync_synchronization_refresh_title)
            isEnabled = saveSyncManager.isConfigured() && !syncInProgress
            summary = context.getString(
                R.string.save_sync_synchronization_refresh_description,
                saveSyncManager.getLastSyncInfo()
            )
            dependency = keySyncEnabled(context)
            isIconSpaceReserved = false
        }

        preferenceScreen.findPreference<CustomMultiListPreference>(keySyncCores(context))?.apply {
        // preferenceScreen.findPreference<MultiSelectListPreference>(keySyncCores(context))?.apply {
            title = context.getString(R.string.save_sync_synchronization_states_title)
            summary = context.getString(R.string.save_sync_synchronization_states_description)
            dependency = keySyncEnabled(context)
            isEnabled = saveSyncManager.isConfigured() && !syncInProgress
            entries = CoreID.values().map { getDisplayNameForCore(context, it) }.toTypedArray()
            entryValues = CoreID.values().map { it.coreName }.toTypedArray()
            isIconSpaceReserved = false
        }
    }

    private fun createCategory(context: Context, title: String): PreferenceCategory {
        val category = PreferenceCategory(context)
        category.title = title
        category.isIconSpaceReserved = false
        return category
    }

    private fun getDisplayNameForCore(context: Context, coreID: CoreID): String {
        val systems = GameSystem.findSystemForCore(coreID)
        val systemHasMultipleCores = systems.any { it.systemCoreConfigs.size > 1 }

        val chunks = mutableListOf<String>().apply {
            add(systems.joinToString(", ") { context.getString(it.shortTitleResId) })

            if (systemHasMultipleCores) {
                add(coreID.coreDisplayName)
            }

            add(saveSyncManager.computeStatesSpace(coreID))
        }

        return chunks.joinToString(" - ")
    }

    fun onPreferenceTreeClick(activity: Activity?, preference: Preference): Boolean {
        val context = preference.context
        return when (preference.key) {
            keyConfigure(context) -> {
                handleSaveSyncConfigure(activity)
                true
            }
            keyForceSync(context) -> {
                handleSaveSyncRefresh(context)
                true
            }
            else -> false
        }
    }

    private fun keySyncEnabled(context: Context) =
        context.getString(R.string.pref_key_save_sync_enable)

    private fun keyForceSync(context: Context) =
        context.getString(R.string.pref_key_save_sync_force_refresh)

    private fun keyConfigure(context: Context) =
        context.getString(R.string.pref_key_save_sync_configure)

    private fun keyAutoSync(context: Context) =
        context.getString(R.string.pref_key_save_sync_auto)

    private fun keySyncCores(context: Context) =
        context.getString(R.string.pref_key_save_sync_cores)

    private fun handleSaveSyncConfigure(activity: Activity?) {
        activity?.startActivity(
            Intent(activity, saveSyncManager.getSettingsActivity())
        )
    }

    private fun handleSaveSyncRefresh(context: Context) {
        SaveSyncWork.enqueueManualWork(context.applicationContext)
    }
}
