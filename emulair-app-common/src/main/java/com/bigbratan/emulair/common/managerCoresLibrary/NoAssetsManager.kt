package com.bigbratan.emulair.common.managerCoresLibrary

import android.content.SharedPreferences
import com.bigbratan.emulair.common.metadataRetrograde.CoreID
import com.bigbratan.emulair.common.managerStorage.DirectoriesManager

class NoAssetsManager : CoreID.AssetsManager {

    override suspend fun clearAssets(directoriesManager: DirectoriesManager) {}

    override suspend fun retrieveAssetsIfNeeded(
        coreUpdaterApi: CoreUpdater.CoreManagerApi,
        directoriesManager: DirectoriesManager,
        sharedPreferences: SharedPreferences
    ) {
    }
}
