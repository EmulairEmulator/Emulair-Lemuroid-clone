package com.bigbratan.emulair.fragments.pauseMenuActions

import android.content.Context
import com.bigbratan.emulair.ui.CustomListPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceGroup
import androidx.preference.PreferenceScreen
import androidx.preference.SwitchPreferenceCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.input.ControllerConfigsManager
import com.bigbratan.emulair.common.managers.controller.ControllerConfig
import com.bigbratan.emulair.common.managers.coresLibrary.CoreVariablesManager
import com.bigbratan.emulair.common.metadata.retrograde.CoreID

object CoreOptionsPreferences {

    private val BOOLEAN_SET = setOf("enabled", "disabled")

    fun addPreferences(
        preferenceScreen: PreferenceScreen,
        systemID: String,
        baseOptions: List<EmulairCoreOption>,
        advancedOptions: List<EmulairCoreOption>,
    ) {
        if (baseOptions.isEmpty() && advancedOptions.isEmpty()) {
            return
        }

        val context = preferenceScreen.context

        // val title = context.getString(R.string.core_settings_category_preferences)
        // val preferencesCategory = createCategory(preferenceScreen.context, preferenceScreen /*, title*/)

        addPreferences(
            context,
            preferenceScreen,
            baseOptions,
            systemID
        ) // replace "preferenceScreen" with "preferencesCategory" when uncommenting above code
        addPreferences(context, preferenceScreen, advancedOptions, systemID)
    }

    fun addControllers(
        preferenceScreen: PreferenceScreen,
        systemID: String,
        coreID: CoreID,
        connectedGamePads: Int,
        controllers: Map<Int, List<ControllerConfig>>,
    ) {
        val visibleControllers = (0 until connectedGamePads)
            .map { it to controllers[it] }
            .filter { (_, controllers) -> controllers != null && controllers.size >= 2 }

        if (visibleControllers.isEmpty())
            return

        val context = preferenceScreen.context
        // val title = context.getString(R.string.core_settings_category_controllers)
        // val category = createCategory(context, preferenceScreen /*, title*/)

        visibleControllers
            .forEach { (port, controllers) ->
                val preference = buildControllerPreference(context, systemID, coreID, port, controllers!!, connectedGamePads)
                preferenceScreen.addPreference(preference) // replace "preferenceScreen" with "category" when uncommenting above code
            }
    }

    private fun addPreferences(
        context: Context,
        preferenceGroup: PreferenceGroup,
        options: List<EmulairCoreOption>,
        systemID: String,
    ) {
        options
            .map { convertToPreference(context, it, systemID) }
            .forEach { preferenceGroup.addPreference(it) }
    }

    private fun convertToPreference(
        context: Context,
        it: EmulairCoreOption,
        systemID: String,
    ): Preference {
        return if (it.getEntriesValues().toSet() == BOOLEAN_SET) {
            buildSwitchPreferenceCompat(context, it, systemID)
        } else {
            buildListPreference(context, it, systemID)
        }
    }

    private fun buildListPreference(
        context: Context,
        it: EmulairCoreOption,
        systemID: String,
    ): CustomListPreference {
        val preference = CustomListPreference(context)
        preference.key = CoreVariablesManager.computeSharedPreferenceKey(it.getKey(), systemID)
        preference.layoutResource = R.layout.layout_preference_pausemenu_list_noresource
        preference.title = it.getDisplayName(context)
        preference.entries = it.getEntries(context).toTypedArray()
        preference.entryValues = it.getEntriesValues().toTypedArray()
        preference.setDefaultValue(it.getCurrentValue())
        preference.setValueIndex(it.getCurrentIndex())
        preference.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        preference.isIconSpaceReserved = false
        return preference
    }

    private fun buildSwitchPreferenceCompat(
        context: Context,
        it: EmulairCoreOption,
        systemID: String,
    ): SwitchPreferenceCompat {
        val preference = SwitchPreferenceCompat(context)
        preference.key = CoreVariablesManager.computeSharedPreferenceKey(it.getKey(), systemID)
        preference.layoutResource = R.layout.layout_preference_pausemenu_switch_noresource
        preference.title = it.getDisplayName(context)
        preference.setDefaultValue(it.getCurrentValue() == "enabled")
        preference.isChecked = it.getCurrentValue() == "enabled"
        preference.isIconSpaceReserved = false
        return preference
    }

    private fun buildControllerPreference(
        context: Context,
        systemID: String,
        coreID: CoreID,
        port: Int,
        controllerConfigs: List<ControllerConfig>,
        connectedGamePads: Int,
    ): Preference {
        val preference = CustomListPreference(context)
        preference.key = ControllerConfigsManager.getSharedPreferencesId(systemID, coreID, port)
        preference.layoutResource = R.layout.layout_preference_pausemenu_list_noresource
        if (connectedGamePads > 1)
            preference.title =
                context.getString(R.string.core_options_controllers_controller_alt, (port + 1).toString())
        else preference.title = context.getString(R.string.core_options_controllers_controller)
        preference.entries = controllerConfigs.map { context.getString(it.displayName) }.toTypedArray()
        preference.entryValues = controllerConfigs.map { it.name }.toTypedArray()
        preference.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        preference.isIconSpaceReserved = false
        preference.setDefaultValue(controllerConfigs.map { it.name }.first())
        return preference
    }

    private fun createCategory(
        context: Context,
        preferenceScreen: PreferenceScreen,
        title: String,
    ): PreferenceCategory {
        val category = PreferenceCategory(context)
        preferenceScreen.addPreference(category)
        category.title = title
        category.isIconSpaceReserved = false
        return category
    }
}
