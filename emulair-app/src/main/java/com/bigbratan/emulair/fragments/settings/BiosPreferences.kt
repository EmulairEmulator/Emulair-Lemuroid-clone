package com.bigbratan.emulair.fragments.settings

import android.content.Context
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceScreen
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.managerBios.BiosManager

class BiosPreferences(private val biosManager: BiosManager) {

    fun addBiosPreferences(preferenceScreen: PreferenceScreen) {
        val context = preferenceScreen.context
        val (installedBios, notInstalledBios) = biosManager.getBiosInfo()

        val detectedBios = createCategory(context, context.getString(R.string.bios_category_detected))
        preferenceScreen.addPreference(detectedBios)
        detectedBios.isVisible = installedBios.isNotEmpty()

        val notDetectedBios = createCategory(context, context.getString(R.string.bios_category_not_detected))
        preferenceScreen.addPreference(notDetectedBios)
        notDetectedBios.isVisible = notInstalledBios.isNotEmpty()

        notInstalledBios.forEachIndexed { index, item ->
            val preference = createBiosPreference(preferenceScreen.context, item)
            if (index == 0) preference.layoutResource = R.layout.layout_preference_main_simple_top
            else if (index == notInstalledBios.size - 1) preference.layoutResource = R.layout.layout_preference_main_simple_bottom
            else preference.layoutResource = R.layout.layout_preference_main_simple_middle
            preference.isEnabled = false
            notDetectedBios.addPreference(preference)
        }

        if (detectedBios.isVisible) {
            detectedBios.layoutResource = R.layout.layout_preference_universal_category_top
            notDetectedBios.layoutResource = R.layout.layout_preference_universal_category_middle

            installedBios.forEachIndexed { index, item ->
                val preference = createBiosPreference(preferenceScreen.context, item)
                preference.isEnabled = true
                detectedBios.addPreference(preference)

                if (installedBios.size == 1) {
                    preference.layoutResource = R.layout.layout_preference_main_simple_block_middle
                } else if (installedBios.size == 2) {
                    if (index == 0) preference.layoutResource = R.layout.layout_preference_main_simple_top
                    else preference.layoutResource = R.layout.layout_preference_main_simple_bottom
                } else if (installedBios.size > 2) {
                    if (index == 0) preference.layoutResource = R.layout.layout_preference_main_simple_top
                    else if (index == installedBios.size - 1) preference.layoutResource =
                        R.layout.layout_preference_main_simple_bottom
                    else preference.layoutResource = R.layout.layout_preference_main_simple_middle
                }
            }
        } else {
            notDetectedBios.layoutResource = R.layout.layout_preference_universal_category_top
        }
    }

    private fun createBiosPreference(context: Context, bios: com.bigbratan.emulair.common.managers.bios.Bios): Preference {
        val preference = Preference(context)
        preference.title = bios.description
        preference.summary = bios.displayName()
        preference.isIconSpaceReserved = false
        return preference
    }

    private fun createCategory(context: Context, title: String): PreferenceCategory {
        val category = PreferenceCategory(context)
        category.title = title
        category.isIconSpaceReserved = false
        return category
    }
}

/*
        installedBios.forEach {
            val preference = createBiosPreference(preferenceScreen.context, it)
            preference.isEnabled = true
            detectedBios.addPreference(preference)
        }

        notInstalledBios.forEach {
            val preference = createBiosPreference(preferenceScreen.context, it)
            preference.isEnabled = false
            notDetectedBios.addPreference(preference)
        }
*/
