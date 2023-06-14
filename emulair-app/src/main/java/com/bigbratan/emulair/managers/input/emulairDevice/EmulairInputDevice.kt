package com.bigbratan.emulair.managers.input.emulairDevice

import android.content.Context
import android.view.InputDevice
import com.bigbratan.emulair.utils.input.InputKey
import com.bigbratan.emulair.utils.input.RetroKey
import com.bigbratan.emulair.managerInput.PauseMenuShortcut

interface EmulairInputDevice {

    fun getCustomizableKeys(): List<RetroKey>

    fun getDefaultBindings(): Map<InputKey, RetroKey>

    fun isSupported(): Boolean

    fun isEnabledByDefault(appContext: Context): Boolean

    fun getSupportedShortcuts(): List<PauseMenuShortcut>
}

fun InputDevice?.getEmulairInputDevice(): EmulairInputDevice {
    return when {
        this == null -> EmulairInputDeviceUnknown
        (sources and InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD -> EmulairInputDeviceGamePad(this)
        (sources and InputDevice.SOURCE_KEYBOARD) == InputDevice.SOURCE_KEYBOARD -> EmulairInputDeviceKeyboard(this)
        else -> EmulairInputDeviceUnknown
    }
}
