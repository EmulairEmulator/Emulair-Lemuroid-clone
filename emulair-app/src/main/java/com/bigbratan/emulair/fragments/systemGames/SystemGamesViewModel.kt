package com.bigbratan.emulair.fragments.systemGames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.bigbratan.emulair.common.utils.paging.buildFlowPaging
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest

class SystemGamesViewModel(private val retrogradeDb: RetrogradeDatabase) : ViewModel() {

    class Factory(val retrogradeDb: RetrogradeDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SystemGamesViewModel(retrogradeDb) as T
        }
    }

    val systemIds = MutableStateFlow<List<String>>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val systemGames: Flow<PagingData<Game>> = systemIds.flatMapLatest {
        when (it.size) {
            0 -> emptyFlow()
            1 -> buildFlowPaging(20) { retrogradeDb.gameDao().selectBySystem(it.first()) }
            else -> buildFlowPaging(20) { retrogradeDb.gameDao().selectBySystems(it) }
        }
    }
}
