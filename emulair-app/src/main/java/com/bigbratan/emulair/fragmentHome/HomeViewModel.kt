package com.bigbratan.emulair.fragmentHome

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.managerCoresLibrary.PendingOperationsMonitor
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class HomeViewModel(appContext: Context, retrogradeDb: RetrogradeDatabase) : ViewModel() {

    companion object {
        const val CAROUSEL_MAX_ITEMS = 10
        const val DEBOUNCE_TIME = 100L
    }

    class Factory(
        val appContext: Context,
        val retrogradeDb: RetrogradeDatabase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(appContext, retrogradeDb) as T
        }
    }

    data class UIState(
        val jumpGame: List<Game> = emptyList(),
        val recentGames: List<Game> = emptyList(),
        val favoriteGames: List<Game> = emptyList(),
        val allGames: List<Game> = emptyList(),
        val indexInProgress: Boolean = false,
        val loading: Boolean = true
    )

    private val uiStates = MutableStateFlow(UIState())

    fun getViewStates(): Flow<UIState> {
        return uiStates
    }

    private fun buildViewState(
        jumpGame: List<Game>,
        recentGames: List<Game>,
        favoriteGames: List<Game>,
        allGames: List<Game>,
        indexInProgress: Boolean
    ): UIState {
        return UIState(jumpGame, recentGames, favoriteGames, allGames, indexInProgress, false)
    }

    init {
        viewModelScope.launch {
            val uiStatesFlow = combine(
                jumpGame(retrogradeDb),
                recentGames(retrogradeDb),
                favoriteGames(retrogradeDb),
                allGames(retrogradeDb),
                indexingInProgress(appContext),
                ::buildViewState
            )

            uiStatesFlow
                .debounce(DEBOUNCE_TIME)
                .flowOn(Dispatchers.IO)
                .collect { uiStates.value = it }
        }
    }

    private fun indexingInProgress(appContext: Context) =
        PendingOperationsMonitor(appContext).anyLibraryOperationInProgress().asFlow()

    private fun jumpGame(retrogradeDb: RetrogradeDatabase) =
        retrogradeDb.gameDao().selectEpoxyJump()

    private fun recentGames(retrogradeDb: RetrogradeDatabase) =
        retrogradeDb.gameDao().selectEpoxyRecents(CAROUSEL_MAX_ITEMS)

    private fun favoriteGames(retrogradeDb: RetrogradeDatabase) =
        retrogradeDb.gameDao().selectEpoxyFavorites(CAROUSEL_MAX_ITEMS)

    private fun allGames(retrogradeDb: RetrogradeDatabase) =
        retrogradeDb.gameDao().selectEpoxyAll(CAROUSEL_MAX_ITEMS)
}
