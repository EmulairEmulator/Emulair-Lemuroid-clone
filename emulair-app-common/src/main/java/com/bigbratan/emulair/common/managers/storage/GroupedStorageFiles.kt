package com.bigbratan.emulair.common.managers.storage

data class GroupedStorageFiles(
    val primaryFile: BaseStorageFile,
    val dataFiles: List<BaseStorageFile>
) {
    fun allFiles() = listOf(primaryFile) + dataFiles
}
