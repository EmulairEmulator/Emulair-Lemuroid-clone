package com.bigbratan.emulair.activityGame

import android.app.Activity
import com.bigbratan.emulair.activityMain.GameLaunchTaskHandler
import com.bigbratan.emulair.common.managerCoresLibrary.CoresSelection
import com.bigbratan.emulair.common.metadataRetrograde.GameSystem
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
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
