package com.bigbratan.emulair.common.managerBios

import com.bigbratan.emulair.common.metadataRetrograde.SystemID

data class Bios(
    val libretroFileName: String,
    val md5: String,
    val description: String,
    val systemID: SystemID,
    val externalCRC32: String? = null,
    val externalName: String? = null,
) {
    fun displayName() = externalName ?: libretroFileName
}
