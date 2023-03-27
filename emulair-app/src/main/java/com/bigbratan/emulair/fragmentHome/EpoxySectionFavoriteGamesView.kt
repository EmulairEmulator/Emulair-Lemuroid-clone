package com.bigbratan.emulair.fragmentHome

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bigbratan.emulair.R

@EpoxyModelClass(layout = R.layout.layout_section_games_alt)
abstract class EpoxySectionFavoriteGamesView : EpoxyModelWithHolder<EpoxySectionFavoriteGamesView.Holder>() {

    @EpoxyAttribute
    var title: Int? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var onClick: (() -> Unit)? = null

    override fun bind(holder: Holder) {
        title?.let {
            holder.titleView.setText(it)
        }

        holder.actionView?.setOnClickListener { onClick?.invoke() }
    }

    override fun unbind(holder: Holder) {
        holder.actionView?.setOnClickListener(null)
    }

    class Holder : EpoxyHolder() {
        lateinit var titleView: TextView
        var actionView: TextView? = null

        override fun bindView(itemView: View) {
            titleView = itemView.findViewById(R.id.text)
            actionView = itemView.findViewById(R.id.action)
        }
    }
}
