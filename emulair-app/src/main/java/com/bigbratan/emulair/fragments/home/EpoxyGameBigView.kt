package com.bigbratan.emulair.fragments.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.interaction.GameContextMenuListener
import com.bigbratan.emulair.managers.interaction.GameInteractor
import com.bigbratan.emulair.managers.covers.CoverLoader
import com.bigbratan.emulair.utils.home.GameUtils
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game

@EpoxyModelClass(layout = R.layout.layout_card_game_orth)
abstract class EpoxyGameBigView : EpoxyModelWithHolder<EpoxyGameBigView.Holder>() {

    @EpoxyAttribute
    lateinit var game: Game

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var gameInteractor: GameInteractor

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var coverLoader: CoverLoader

    override fun bind(holder: Holder) {
        holder.titleView?.text = game.title
        holder.subtitleView?.let { it.text = GameUtils.getGameSubtitle(it.context, game) }
        holder.favoriteToggle?.isChecked = game.isFavorite

        coverLoader.loadCover(game, holder.coverView)

        holder.itemView?.setOnClickListener { gameInteractor.onGamePlay(game) }
        holder.itemView?.setOnCreateContextMenuListener(
            GameContextMenuListener(gameInteractor, game)
        )

        holder.favoriteToggle?.setOnCheckedChangeListener { _, isChecked ->
            gameInteractor.onFavoriteToggle(game, isChecked)
        }
    }

    override fun unbind(holder: Holder) {
        holder.itemView?.setOnClickListener(null)
        holder.coverView?.apply {
            coverLoader.cancelRequest(this)
        }
        holder.favoriteToggle?.setOnCheckedChangeListener(null)
        holder.itemView?.setOnCreateContextMenuListener(null)
    }

    class Holder : EpoxyHolder() {
        var itemView: View? = null
        var titleView: TextView? = null
        var subtitleView: TextView? = null
        var coverView: ImageView? = null
        var favoriteToggle: ToggleButton? = null

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.titleView = itemView.findViewById(R.id.text)
            this.subtitleView = itemView.findViewById(R.id.subtext)
            this.coverView = itemView.findViewById(R.id.image)
            this.favoriteToggle = itemView.findViewById(R.id.favorite_toggle)
        }
    }
}
