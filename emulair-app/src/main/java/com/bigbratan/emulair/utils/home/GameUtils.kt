package com.bigbratan.emulair.utils.home

import android.content.Context
import com.bigbratan.emulair.common.metadataRetrograde.GameSystem
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game

class GameUtils {

    companion object {
        fun getGameSubtitle(context: Context, game: Game): String {
            val systemName = getSystemNameForGame(context, game)
            // val manufacturerName = getManufacturerNameForSystem(context, game)
            return "$systemName"
        }

        private fun getSystemNameForGame(context: Context, game: Game): String {
            val systemTitleResource = GameSystem.findById(game.systemId).titleResId
            return context.getString(systemTitleResource)
        }

        /*
        private fun getManufacturerNameForSystem(context: Context, game: Game): String {
            val manufacturerTitleResource =
            return context.getString(manufacturerTitleResource)
        }
        */
    }
}
