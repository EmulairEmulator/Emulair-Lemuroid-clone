package com.bigbratan.emulair.fragmentSystems

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bigbratan.emulair.utils.systems.SystemUtils
import com.bigbratan.emulair.common.metadataRetrograde.GameSystem
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
import com.bigbratan.emulair.common.metadataRetrograde.metaSystemID
import com.bigbratan.emulair.fragmentHome.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SystemsViewModel(retrogradeDb: RetrogradeDatabase, appContext: Context) : ViewModel() {

    class Factory(
        val retrogradeDb: RetrogradeDatabase,
        val appContext: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SystemsViewModel(retrogradeDb, appContext) as T
        }
    }

    val metaSystems: Flow<List<SystemUtils>> = retrogradeDb.gameDao()
        .selectMetaSystemsWithCount()
        .map { systemCounts ->
            systemCounts.asSequence()
                .filter { (_, count) -> count > 0 }
                .map { (systemId, count) -> GameSystem.findById(systemId).metaSystemID() to count }
                .groupBy { (metaSystemId, _) -> metaSystemId }
                .map { (metaSystemId, counts) -> SystemUtils(metaSystemId, counts.sumBy { it.second }) }
                .sortedBy { it.getName(appContext) }
                .toList()
        }
}

/*
    fun getViewStates(): Flow<UIState> {
        return uiStates
    }

    private val uiStates = MutableStateFlow(UIState())

    data class UIState(
        val starredSystems: List<SystemUtils> = emptyList(),
        val nonStarredSystems: List<SystemUtils> = emptyList(),
    )

    private fun buildViewState(
        starredSystems: List<SystemUtils>,
        nonStarredSystems: List<SystemUtils>
    ): UIState {
        return UIState(starredSystems, nonStarredSystems)
    }

    init {
        viewModelScope.launch {
            val uiStatesFlow = combine(
                starredMetaSystems(retrogradeDb, appContext),
                nonStarredMetaSystems(retrogradeDb, appContext),
                ::buildViewState
            )

            uiStatesFlow
                .flowOn(Dispatchers.IO)
                .collect { uiStates.value = it }
        }
    }

    private fun nonStarredMetaSystems(retrogradeDb: RetrogradeDatabase, appContext: Context) = retrogradeDb.gameDao()
        .selectNonStarredSystemsWithCount()
        .map { systemCounts ->
            systemCounts.asSequence()
                .filter { (_, count) -> count > 0 }
                .map { (systemId, count) -> GameSystem.findById(systemId).metaSystemID() to count }
                .groupBy { (metaSystemId, _) -> metaSystemId }
                .map { (metaSystemId, counts) -> SystemUtils(metaSystemId, counts.sumBy { it.second }) }
                .sortedBy { it.getName(appContext) }
                .toList()
        }

    private fun starredMetaSystems(retrogradeDb: RetrogradeDatabase, appContext: Context) = retrogradeDb.gameDao()
        .selectStarredSystemsWithCount()
        .map { systemCounts ->
            systemCounts.asSequence()
                .filter { (_, count) -> count > 0 }
                .map { (systemId, count) -> GameSystem.findById(systemId).metaSystemID() to count }
                .groupBy { (metaSystemId, _) -> metaSystemId }
                .map { (metaSystemId, counts) -> SystemUtils(metaSystemId, counts.sumBy { it.second }) }
                .sortedBy { it.getName(appContext) }
                .toList()
        }
*/

/*
    val nonStarredMetaSystems: Flow<List<SystemUtils>> = retrogradeDb.gameDao()
        .selectNonStarredSystemsWithCount()
        .map { systemCounts ->
            systemCounts.asSequence()
                .filter { (_, count) -> count > 0 }
                .map { (systemId, count) -> GameSystem.findById(systemId).metaSystemID() to count }
                .groupBy { (metaSystemId, _) -> metaSystemId }
                .map { (metaSystemId, counts) -> SystemUtils(metaSystemId, counts.sumBy { it.second }) }
                .sortedBy { it.getName(appContext) }
                .toList()
        }

    val starredMetaSystems: Flow<List<SystemUtils>> = retrogradeDb.gameDao()
        .selectStarredSystemsWithCount()
        .map { systemCounts ->
            systemCounts.asSequence()
                .filter { (_, count) -> count > 0 }
                .map { (systemId, count) -> GameSystem.findById(systemId).metaSystemID() to count }
                .groupBy { (metaSystemId, _) -> metaSystemId }
                .map { (metaSystemId, counts) -> SystemUtils(metaSystemId, counts.sumBy { it.second }) }
                .sortedBy { it.getName(appContext) }
                .toList()
        }
*/
