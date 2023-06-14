package com.bigbratan.emulair.fragments.settings

import android.content.Context
import android.content.Intent
import android.view.InputDevice
import android.view.KeyEvent
import com.bigbratan.emulair.ui.CustomListPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceScreen
import androidx.preference.SwitchPreference
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.input.InputBindingUpdater
import com.bigbratan.emulair.managers.input.InputDeviceManager
import com.bigbratan.emulair.managers.input.PauseMenuShortcut
import com.bigbratan.emulair.utils.input.InputKey
import com.bigbratan.emulair.utils.input.RetroKey
import com.bigbratan.emulair.managers.input.emulairDevice.getEmulairInputDevice
import java.util.Locale

class GamePadPreferences(
    private val inputDeviceManager: InputDeviceManager,
) {
    suspend fun addGamePadsPreferencesToScreen(
        context: Context,
        preferenceScreen: PreferenceScreen,
        gamePads: List<InputDevice>,
        enabledGamePads: List<InputDevice>,
    ) {
        val distinctGamePads = getDistinctGamePads(gamePads)
        val distinctEnabledGamePads = getDistinctGamePads(enabledGamePads)

        addEnabledCategory(context, preferenceScreen, distinctGamePads)

        distinctEnabledGamePads
            .forEach {
                addPreferenceCategoryForInputDevice(context, preferenceScreen, it)
            }

        addExtraCategory(context, preferenceScreen, gamePads)

        refreshGamePadsPreferencesToScreen(preferenceScreen, distinctEnabledGamePads)
    }

    suspend fun refreshGamePadsPreferencesToScreen(
        preferenceScreen: PreferenceScreen,
        enabledGamePads: List<InputDevice>,
    ) {
        getDistinctGamePads(enabledGamePads)
            .forEach { refreshPreferenceCategoryForInputDevice(preferenceScreen, it) }
    }

    private fun getDistinctGamePads(gamePads: List<InputDevice>): List<InputDevice> {
        return gamePads.distinctBy { it.descriptor }
    }

    private fun addEnabledCategory(
        context: Context,
        preferenceScreen: PreferenceScreen,
        gamePads: List<InputDevice>,
    ) {
        if (gamePads.isEmpty())
            return

        val categoryTitle = context.resources.getString(R.string.gamepad_category_devices)
        val category = createCategory(context, preferenceScreen, categoryTitle)
        category.layoutResource = R.layout.layout_preference_universal_category_top
        preferenceScreen.addPreference(category)

        gamePads.forEach { gamePad ->
            category.addPreference(buildGamePadEnabledPreference(context, gamePad))
        }
    }

    private fun addExtraCategory(context: Context, preferenceScreen: PreferenceScreen, gamePads: List<InputDevice>) {
        val categoryTitle = context.resources.getString(R.string.gamepad_category_general)
        val category = createCategory(context, preferenceScreen, categoryTitle)

        if (gamePads.isEmpty())
            category.layoutResource = R.layout.layout_preference_universal_category_top
        else
            category.layoutResource = R.layout.layout_preference_universal_category_middle

        Preference(context).apply {
            layoutResource = R.layout.layout_preference_main_simple_block_middle
            key = context.resources.getString(R.string.pref_key_reset_gamepad_bindings)
            title = context.resources.getString(R.string.gamepad_general_reset_bindings_title)
            isIconSpaceReserved = false
            category.addPreference(this)
        }
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

    private fun addPreferenceCategoryForInputDevice(
        context: Context,
        preferenceScreen: PreferenceScreen,
        inputDevice: InputDevice,
    ) {
        val category = createCategory(context, preferenceScreen, inputDevice.name)
        category.layoutResource = R.layout.layout_preference_universal_category_middle
        preferenceScreen.addPreference(category)

        inputDevice.getEmulairInputDevice().getCustomizableKeys()
            .map { buildKeyBindingPreference(context, inputDevice, it) }
            .forEachIndexed { index, item ->
                if (index == 0) item.layoutResource = R.layout.layout_preference_main_simple_top
                else item.layoutResource = R.layout.layout_preference_main_simple_middle
                category.addPreference(item)
            }

        buildGameMenuShortcutPreference(context, inputDevice)?.let {
            category.addPreference(it)
        }
    }

    private suspend fun refreshPreferenceCategoryForInputDevice(
        preferenceScreen: PreferenceScreen,
        inputDevice: InputDevice,
    ) {
        val inverseBindings: Map<RetroKey, InputKey> = inputDeviceManager.getBindings(inputDevice)
            .map { it.value to it.key }
            .toMap()

        inputDevice.getEmulairInputDevice().getCustomizableKeys()
            .forEach { retroKey ->
                val boundKey = inverseBindings[retroKey]?.keyCode ?: KeyEvent.KEYCODE_UNKNOWN
                val preferenceKey = InputDeviceManager.computeKeyBindingRetroKeyPreference(inputDevice, retroKey)
                val preference = preferenceScreen.findPreference<Preference>(preferenceKey)
                preference?.summaryProvider = Preference.SummaryProvider<Preference> {
                    displayNameForKeyCode(boundKey)
                }
            }
    }

    private fun buildGamePadEnabledPreference(
        context: Context,
        inputDevice: InputDevice,
    ): Preference {
        val preference = SwitchPreference(context)
        preference.layoutResource = R.layout.layout_preference_main_switch_block_middle
        preference.key = InputDeviceManager.computeEnabledGamePadPreference(inputDevice)
        preference.title = inputDevice.name
        preference.setDefaultValue(inputDevice.getEmulairInputDevice().isEnabledByDefault(context))
        preference.isChecked = true
        preference.isIconSpaceReserved = false
        return preference
    }

    private fun buildKeyBindingPreference(
        context: Context,
        inputDevice: InputDevice,
        retroKey: RetroKey,
    ): Preference {
        val preference = Preference(context)
        preference.key = InputDeviceManager.computeKeyBindingRetroKeyPreference(inputDevice, retroKey)
        preference.title = getRetroPadKeyName(context, retroKey.keyCode)
        preference.setOnPreferenceClickListener {
            displayChangeDialog(context, inputDevice, retroKey.keyCode)
            true
        }
        preference.isIconSpaceReserved = false
        return preference
    }

    private fun displayChangeDialog(context: Context, inputDevice: InputDevice, retroKey: Int) {
        val activity = GamePadBindingActivity::class.java

        val intent = Intent(context, activity).apply {
            putExtra(InputBindingUpdater.REQUEST_DEVICE, inputDevice)
            putExtra(InputBindingUpdater.REQUEST_RETRO_KEY, retroKey)
        }
        context.startActivity(intent)
    }

    private fun buildGameMenuShortcutPreference(
        context: Context,
        inputDevice: InputDevice,
    ): Preference? {
        val default = PauseMenuShortcut.getDefault(inputDevice) ?: return null
        val supportedShortcuts = inputDevice.getEmulairInputDevice().getSupportedShortcuts()

        val preference = CustomListPreference(context)
        preference.key = InputDeviceManager.computeGameMenuShortcutPreference(inputDevice)
        preference.layoutResource = R.layout.layout_preference_main_list_bottom
        preference.title = context.getString(R.string.gamepad_retropad_game_menu_title)
        preference.entries = supportedShortcuts.map { it.name }.toTypedArray()
        preference.entryValues = supportedShortcuts.map { it.name }.toTypedArray()
        preference.setValueIndex(supportedShortcuts.indexOf(default))
        preference.setDefaultValue(default.name)
        preference.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
        preference.isIconSpaceReserved = false
        return preference
    }

    private fun getRetroPadKeyName(context: Context, key: Int): String {
        return context.resources.getString(
            R.string.gamepad_devices_retropad_button_title,
            displayNameForKeyCode(key)
        )
    }

    companion object {
        fun displayNameForKeyCode(keyCode: Int): String {
            return when (keyCode) {
                KeyEvent.KEYCODE_BUTTON_THUMBL -> "L3"
                KeyEvent.KEYCODE_BUTTON_THUMBR -> "R3"
                KeyEvent.KEYCODE_BUTTON_MODE -> "Options"
                KeyEvent.KEYCODE_UNKNOWN -> " - "
                else ->
                    KeyEvent.keyCodeToString(keyCode)
                        .split("_")
                        .last()
                        .lowercase()
                        .replaceFirstChar { it.titlecase(Locale.ENGLISH) }
            }
        }
    }

    @dagger.Module
    class Module
}
