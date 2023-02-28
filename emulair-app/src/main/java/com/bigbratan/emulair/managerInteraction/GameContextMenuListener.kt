package com.bigbratan.emulair.managerInteraction

import android.view.ContextMenu
import android.view.View
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game

class GameContextMenuListener(
    private val gameInteractor: GameInteractor,
    private val game: Game
) : View.OnCreateContextMenuListener {

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu.add(R.string.game_context_menu_option_resume).setOnMenuItemClickListener {
            gameInteractor.onGamePlay(game)
            true
        }

        menu.add(R.string.game_context_menu_option_restart).setOnMenuItemClickListener {
            gameInteractor.onGameRestart(game)
            true
        }

        if (game.isFavorite) {
            menu.add(R.string.game_context_menu_option_remove_from_favorites).setOnMenuItemClickListener {
                gameInteractor.onFavoriteToggle(game, false)
                true
            }
        } else {
            menu.add(R.string.game_context_menu_option_add_to_favorites).setOnMenuItemClickListener {
                gameInteractor.onFavoriteToggle(game, true)
                true
            }
        }

        /*
        if (gameInteractor.supportShortcuts()) {
            menu.add(R.string.game_context_menu_create_shortcut).setOnMenuItemClickListener {
                gameInteractor.onCreateShortcut(game)
                true
            }
        }
        */
    }
}
