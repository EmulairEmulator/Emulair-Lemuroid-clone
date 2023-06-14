package com.bigbratan.emulair.managers.input.inputClass

import com.bigbratan.emulair.utils.input.InputKey

object InputClassUnknown : InputClass {
    override fun getInputKeys(): Set<InputKey> {
        return emptySet()
    }

    override fun getAxesMap(): Map<Int, Int> {
        return emptyMap()
    }
}
