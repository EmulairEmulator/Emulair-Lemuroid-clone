package com.bigbratan.emulair.common.managerStorage

data class GroupedStorageFiles(
    val primaryFile: BaseStorageFile,
    val dataFiles: List<BaseStorageFile>
) {
    fun allFiles() = listOf(primaryFile) + dataFiles
}
