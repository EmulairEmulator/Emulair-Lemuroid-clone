package com.bigbratan.emulair.managerInput

import android.view.InputDevice
import com.bigbratan.emulair.managerInput.emulairDevice.getEmulairInputDevice

data class PauseMenuShortcut(val name: String, val keys: Set<Int>) {

    companion object {

        fun getDefault(inputDevice: InputDevice): PauseMenuShortcut? {
            return inputDevice.getEmulairInputDevice()
                .getSupportedShortcuts()
                .firstOrNull { shortcut ->
                    inputDevice.hasKeys(*(shortcut.keys.toIntArray())).all { it }
                }
        }

        fun findByName(device: InputDevice, name: String): PauseMenuShortcut? {
            return device.getEmulairInputDevice()
                .getSupportedShortcuts()
                .firstOrNull { it.name == name }
        }
    }
}
