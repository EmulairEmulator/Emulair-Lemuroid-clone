package com.bigbratan.emulair.fragments.favoriteGames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import com.bigbratan.emulair.common.utils.paging.buildFlowPaging
import kotlinx.coroutines.flow.Flow

class FavoriteGamesViewModel(retrogradeDb: RetrogradeDatabase) : ViewModel() {

    class Factory(val retrogradeDb: RetrogradeDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoriteGamesViewModel(retrogradeDb) as T
        }
    }

    val favoriteGames: Flow<PagingData<Game>> =
        buildFlowPaging(20) { retrogradeDb.gameDao().selectFavorites() }
}
