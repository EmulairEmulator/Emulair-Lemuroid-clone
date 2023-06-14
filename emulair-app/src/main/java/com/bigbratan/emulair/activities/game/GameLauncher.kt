package com.bigbratan.emulair.activities.game

import android.app.Activity
import com.bigbratan.emulair.activities.main.GameLaunchTaskHandler
import com.bigbratan.emulair.common.managers.coresLibrary.CoresSelection
import com.bigbratan.emulair.common.metadata.retrograde.GameSystem
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameLauncher(
    private val coresSelection: CoresSelection,
    private val gameLaunchTaskHandler: GameLaunchTaskHandler
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun launchGameAsync(activity: Activity, game: Game, loadSave: Boolean) {
        GlobalScope.launch {
            val system = GameSystem.findById(game.systemId)
            val coreConfig = coresSelection.getCoreConfigForSystem(system)
            gameLaunchTaskHandler.handleGameStart(activity.applicationContext)
            BaseGameActivity.launchGame(activity, coreConfig, game, loadSave)
        }
    }
}
