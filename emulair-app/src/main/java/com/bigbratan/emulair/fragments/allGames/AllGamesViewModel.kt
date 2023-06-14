package com.bigbratan.emulair.fragments.allGames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.bigbratan.emulair.common.utils.paging.buildFlowPaging
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import kotlinx.coroutines.flow.Flow

class AllGamesViewModel(retrogradeDb: RetrogradeDatabase) : ViewModel() {

    class Factory(val retrogradeDb: RetrogradeDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllGamesViewModel(retrogradeDb) as T
        }
    }

    val allGames: Flow<PagingData<Game>> =
        buildFlowPaging(20) { retrogradeDb.gameDao().selectAll() }
}
