package com.bigbratan.emulair.managerInput.inputClass

import com.bigbratan.emulair.utils.input.InputKey

object InputClassUnknown : InputClass {
    override fun getInputKeys(): Set<InputKey> {
        return emptySet()
    }

    override fun getAxesMap(): Map<Int, Int> {
        return emptyMap()
    }
}
