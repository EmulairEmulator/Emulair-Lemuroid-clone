package com.bigbratan.emulair.common.managers.coresLibrary

import android.content.SharedPreferences
import com.bigbratan.emulair.common.metadata.retrograde.GameSystem
import com.bigbratan.emulair.common.metadata.retrograde.SystemCoreConfig
import com.bigbratan.emulair.common.metadata.retrograde.SystemID
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoresSelection(private val sharedPreferences: Lazy<SharedPreferences>) {

    suspend fun getCoreConfigForSystem(system: GameSystem) = withContext(Dispatchers.IO) {
        fetchSystemCoreConfig(system)
    }

    private fun fetchSystemCoreConfig(system: GameSystem): SystemCoreConfig {
        val setting = sharedPreferences.get()
            .getString(computeSystemPreferenceKey(system.id), null)

        return system.systemCoreConfigs.firstOrNull { it.coreID.coreName == setting }
            ?: system.systemCoreConfigs.first()
    }

    companion object {
        private const val CORE_SELECTION_BINDING_PREFERENCE_BASE_KEY = "pref_key_core_selection"

        fun computeSystemPreferenceKey(systemID: SystemID) =
            "${CORE_SELECTION_BINDING_PREFERENCE_BASE_KEY}_${systemID.dbname}"
    }
}
