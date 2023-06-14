package com.bigbratan.emulair.managers.input.emulairDevice

import android.content.Context
import android.view.InputDevice
import android.view.KeyEvent
import com.bigbratan.emulair.managers.input.InputDeviceManager
import com.bigbratan.emulair.utils.input.InputKey
import com.bigbratan.emulair.utils.input.RetroKey
import com.bigbratan.emulair.managers.input.inputClass.getInputClass
import com.bigbratan.emulair.utils.input.bindingsOf
import com.bigbratan.emulair.utils.input.inputKeysOf
import com.bigbratan.emulair.utils.input.retroKeysOf
import com.bigbratan.emulair.utils.input.supportsAllKeys
import com.bigbratan.emulair.managers.input.PauseMenuShortcut

class EmulairInputDeviceGamePad(private val device: InputDevice) : EmulairInputDevice {

    override fun getDefaultBindings(): Map<InputKey, RetroKey> {
        val allAvailableInputs = InputDeviceManager.OUTPUT_KEYS
            .associate {
                InputKey(it.keyCode) to getDefaultBindingForKey(device, it)
            }

        val defaultOverride = bindingsOf(
            KeyEvent.KEYCODE_BUTTON_A to KeyEvent.KEYCODE_BUTTON_B,
            KeyEvent.KEYCODE_BUTTON_B to KeyEvent.KEYCODE_BUTTON_A,
            KeyEvent.KEYCODE_BUTTON_X to KeyEvent.KEYCODE_BUTTON_Y,
            KeyEvent.KEYCODE_BUTTON_Y to KeyEvent.KEYCODE_BUTTON_X
        )

        return allAvailableInputs + defaultOverride
    }

    private fun getDefaultBindingForKey(
        device: InputDevice,
        it: RetroKey
    ): RetroKey {
        val defaultBinding = if (device.hasKeys(it.keyCode).first()) {
            RetroKey(it.keyCode)
        } else {
            RetroKey(KeyEvent.KEYCODE_UNKNOWN)
        }
        return defaultBinding
    }

    override fun isEnabledByDefault(appContext: Context): Boolean {
        return device.supportsAllKeys(MINIMAL_KEYS_DEFAULT_ENABLED)
    }

    override fun getSupportedShortcuts(): List<PauseMenuShortcut> = listOf(
        PauseMenuShortcut(
            "L3 + R3",
            setOf(KeyEvent.KEYCODE_BUTTON_THUMBL, KeyEvent.KEYCODE_BUTTON_THUMBR)
        ),
        PauseMenuShortcut(
            "Select + Start",
            setOf(KeyEvent.KEYCODE_BUTTON_START, KeyEvent.KEYCODE_BUTTON_SELECT)
        )
    )

    override fun isSupported(): Boolean {
        return sequenceOf(
            device.sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD,
            device.supportsAllKeys(MINIMAL_SUPPORTED_KEYS),
            device.isVirtual.not(),
            device.controllerNumber > 0
        ).all { it }
    }

    override fun getCustomizableKeys(): List<RetroKey> {
        val deviceAxis = device.motionRanges
            .map { it.axis }
            .toSet()

        val keysMappedToAxis = device.getInputClass().getAxesMap()
            .filter { it.key in deviceAxis }
            .map { it.value }
            .toSet()

        return CUSTOMIZABLE_KEYS
            .filter { it.keyCode !in keysMappedToAxis }
    }

    companion object {

        private val MINIMAL_SUPPORTED_KEYS = inputKeysOf(
            KeyEvent.KEYCODE_BUTTON_A,
            KeyEvent.KEYCODE_BUTTON_B,
            KeyEvent.KEYCODE_BUTTON_X,
            KeyEvent.KEYCODE_BUTTON_Y,
        )

        private val MINIMAL_KEYS_DEFAULT_ENABLED = MINIMAL_SUPPORTED_KEYS + inputKeysOf(
            KeyEvent.KEYCODE_BUTTON_START,
            KeyEvent.KEYCODE_BUTTON_SELECT,
        )

        private val CUSTOMIZABLE_KEYS: List<RetroKey> = retroKeysOf(
            KeyEvent.KEYCODE_BUTTON_A,
            KeyEvent.KEYCODE_BUTTON_B,
            KeyEvent.KEYCODE_BUTTON_X,
            KeyEvent.KEYCODE_BUTTON_Y,
            KeyEvent.KEYCODE_BUTTON_START,
            KeyEvent.KEYCODE_BUTTON_SELECT,
            KeyEvent.KEYCODE_BUTTON_L1,
            KeyEvent.KEYCODE_BUTTON_L2,
            KeyEvent.KEYCODE_BUTTON_R1,
            KeyEvent.KEYCODE_BUTTON_R2,
            KeyEvent.KEYCODE_BUTTON_THUMBL,
            KeyEvent.KEYCODE_BUTTON_THUMBR,
            KeyEvent.KEYCODE_BUTTON_MODE,
        )
    }
}
