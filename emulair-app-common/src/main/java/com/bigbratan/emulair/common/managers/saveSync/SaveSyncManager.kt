package com.bigbratan.emulair.common.managers.saveSync

import android.app.Activity
import com.bigbratan.emulair.common.metadata.retrograde.CoreID

interface SaveSyncManager {
    fun getProvider(): String
    fun getSettingsActivity(): Class<out Activity>?
    fun isSupported(): Boolean
    fun isConfigured(): Boolean
    fun getLastSyncInfo(): String
    fun getConfigInfo(): String
    suspend fun sync(cores: Set<CoreID>)
    fun computeSavesSpace(): String
    fun computeStatesSpace(core: CoreID): String
}
