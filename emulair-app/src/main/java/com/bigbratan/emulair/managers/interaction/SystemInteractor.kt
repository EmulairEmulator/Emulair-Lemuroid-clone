/*
package com.bigbratan.emulair.managers.interaction

import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.metadataRetrograde.MetaSystemID
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SystemInteractor(
    private val retrogradeDb: RetrogradeDatabase,
    private val onSystemClick: (MetaSystemID) -> Unit
) {
    @OptIn(DelicateCoroutinesApi::class)
    fun onStarToggle(game: Game, isStarred: Boolean) {
        GlobalScope.launch {
            retrogradeDb.gameDao().update(game.copy(isStarred = isStarred))
        }
    }
}
*/
