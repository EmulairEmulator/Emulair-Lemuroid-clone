package com.bigbratan.emulair.managers.interaction

import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.game.GameLauncher
import com.bigbratan.emulair.activities.main.BusyActivity
import com.bigbratan.emulair.utils.displayToast
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameInteractor(
    private val activity: BusyActivity,
    private val retrogradeDb: RetrogradeDatabase,
    private val gameLauncher: GameLauncher
) {
    fun onGamePlay(game: Game) {
        if (activity.isBusy()) {
            activity.activity().displayToast(R.string.game_toast_interactor_busy)
            return
        }
        gameLauncher.launchGameAsync(activity.activity(), game, true)
    }

    fun onGameRestart(game: Game) {
        if (activity.isBusy()) {
            activity.activity().displayToast(R.string.game_toast_interactor_busy)
            return
        }
        gameLauncher.launchGameAsync(activity.activity(), game, false)
    }

    fun onFavoriteToggle(game: Game, isFavorite: Boolean) {
        GlobalScope.launch {
            retrogradeDb.gameDao().update(game.copy(isFavorite = isFavorite))
        }
    }
}
