package com.bigbratan.emulair.managers.settings

import android.content.Context
import com.bigbratan.emulair.managerCoresLibrary.LibraryIndexScheduler
import com.bigbratan.emulair.managerStorage.cache.CacheCleanerWork
import com.bigbratan.emulair.common.managerPreferences.SharedPreferencesHelper
import com.bigbratan.emulair.common.managerStorage.DirectoriesManager

class SettingsInteractor(
    private val context: Context,
    private val directoriesManager: DirectoriesManager
) {
    fun changeLocalStorageFolder() {
        StorageFrameworkPickerLauncher.pickFolder(context)
    }

    fun resetAllSettings() {
        SharedPreferencesHelper.getLegacySharedPreferences(context).edit().clear().apply()
        SharedPreferencesHelper.getSharedPreferences(context).edit().clear().apply()
        LibraryIndexScheduler.scheduleLibrarySync(context.applicationContext)
        CacheCleanerWork.enqueueCleanCacheAll(context.applicationContext)
        deleteDownloadedCores()
    }

    private fun deleteDownloadedCores() {
        directoriesManager.getCoresDirectory()
            .listFiles()
            ?.forEach { runCatching { it.deleteRecursively() } }
    }
}
