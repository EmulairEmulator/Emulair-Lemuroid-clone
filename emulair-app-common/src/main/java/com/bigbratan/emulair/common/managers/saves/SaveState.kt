package com.bigbratan.emulair.common.managers.saves

import kotlinx.serialization.Serializable

class SaveState(val state: ByteArray, val metadata: Metadata) {
    @Serializable
    data class Metadata(val diskIndex: Int = 0, val version: Int = 0)
}
