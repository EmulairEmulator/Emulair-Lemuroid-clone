/*
package com.bigbratan.emulair.fragmentSystems

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.DiffUtil
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.metadataRetrograde.MetaSystemID
import com.bigbratan.emulair.managerInteraction.GameContextMenuListener
import com.bigbratan.emulair.managerInteraction.GameInteractor
import com.bigbratan.emulair.managerCovers.CoverLoader
import com.bigbratan.emulair.utils.home.GameUtils
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
import com.bigbratan.emulair.utils.systems.SystemUtils

@EpoxyModelClass(layout = R.layout.layout_card_game_horz2)
abstract class EpoxySystemView : EpoxyModelWithHolder<EpoxySystemView.Holder>() {

    @EpoxyAttribute
    lateinit var systemUtils: SystemUtils

    @EpoxyAttribute
    lateinit var onSystemClick: (MetaSystemID) -> Unit

    override fun bind(holder: Holder) {
        holder.textView?.text = holder.itemView?.context?.resources?.getString(systemUtils.metaSystemId.titleResId)
        holder.subtextView?.text = holder.itemView?.context?.getString(
            R.string.systems_subtitle,
            systemUtils.count.toString()
        )
        holder.coverView?.setImageResource(systemUtils.metaSystemId.imageResId)
        holder.itemView?.setOnClickListener { onSystemClick(systemUtils.metaSystemId) }
    }

    class Holder : EpoxyHolder() {
        var itemView: View? = null
        var textView: TextView? = null
        var subtextView: TextView? = null
        var coverView: ImageView? = null
        //var starredToggle: ToggleButton? = null

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.textView = itemView.findViewById(R.id.text)
            this.subtextView = itemView.findViewById(R.id.subtext)
            this.coverView = itemView.findViewById(R.id.image)
            //this.starredToggle = itemView.findViewById(R.id.starred_toggle)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SystemUtils>() {

            override fun areItemsTheSame(oldInfo: SystemUtils, newInfo: SystemUtils) =
                oldInfo.metaSystemId == newInfo.metaSystemId

            override fun areContentsTheSame(oldInfo: SystemUtils, newInfo: SystemUtils) =
                oldInfo == newInfo
        }
    }
}
*/
