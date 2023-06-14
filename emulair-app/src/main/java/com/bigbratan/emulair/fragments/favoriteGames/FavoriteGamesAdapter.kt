package com.bigbratan.emulair.fragments.favoriteGames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.covers.CoverLoader
import com.bigbratan.emulair.utils.home.GameUtils
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import com.bigbratan.emulair.managers.interaction.GameContextMenuListener
import com.bigbratan.emulair.managers.interaction.GameInteractor

class FavoriteGameViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
    private var titleView: TextView? = null
    private var subtitleView: TextView? = null
    private var coverView: ImageView? = null
    private var favoriteToggle: ToggleButton? = null

    init {
        titleView = itemView.findViewById(R.id.text)
        subtitleView = itemView.findViewById(R.id.subtext)
        coverView = itemView.findViewById(R.id.image)
        favoriteToggle = itemView.findViewById(R.id.favorite_toggle)
    }

    fun bind(game: Game, gameInteractor: GameInteractor, coverLoader: CoverLoader) {
        titleView?.text = game.title
        subtitleView?.text = GameUtils.getGameSubtitle(itemView.context, game)
        favoriteToggle?.isChecked = game.isFavorite

        coverLoader.loadCover(game, coverView)

        itemView.setOnClickListener { gameInteractor.onGamePlay(game) }
        itemView.setOnCreateContextMenuListener(GameContextMenuListener(gameInteractor, game))

        favoriteToggle?.setOnCheckedChangeListener { _, isChecked ->
            gameInteractor.onFavoriteToggle(game, isChecked)
        }
    }

    fun unbind(coverLoader: CoverLoader) {
        coverView?.apply {
            coverLoader.cancelRequest(this)
            this.setImageDrawable(null)
        }
        itemView.setOnClickListener(null)
        favoriteToggle?.setOnCheckedChangeListener(null)
        itemView.setOnCreateContextMenuListener(null)
    }
}

class FavoriteGamesAdapter(
    private val baseLayout: Int,
    private val gameInteractor: GameInteractor,
    private val coverLoader: CoverLoader
) : PagingDataAdapter<Game, FavoriteGameViewHolder>(Game.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteGameViewHolder {
        return FavoriteGameViewHolder(LayoutInflater.from(parent.context).inflate(baseLayout, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteGameViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, gameInteractor, coverLoader) }
    }

    override fun onViewRecycled(holder: FavoriteGameViewHolder) {
        holder.unbind(coverLoader)
    }
}
