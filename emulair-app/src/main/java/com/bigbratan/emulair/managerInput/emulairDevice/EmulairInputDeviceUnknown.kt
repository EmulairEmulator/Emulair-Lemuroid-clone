package com.bigbratan.emulair.managerInput.emulairDevice

import android.content.Context
import com.bigbratan.emulair.utils.input.InputKey
import com.bigbratan.emulair.utils.input.RetroKey
import com.bigbratan.emulair.managerInput.PauseMenuShortcut

object EmulairInputDeviceUnknown : EmulairInputDevice {
    override fun getDefaultBindings(): Map<InputKey, RetroKey> = emptyMap()

    override fun isSupported(): Boolean = false

    override fun isEnabledByDefault(appContext: Context): Boolean = false

    override fun getSupportedShortcuts(): List<PauseMenuShortcut> = emptyList()

    override fun getCustomizableKeys(): List<RetroKey> = emptyList()
}
