package com.bigbratan.emulair.fragmentHome

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bigbratan.emulair.R

@EpoxyModelClass(layout = R.layout.layout_section_games)
abstract class EpoxySectionView : EpoxyModelWithHolder<EpoxySectionView.Holder>() {

    @EpoxyAttribute
    var title: Int? = null

    override fun bind(holder: Holder) {
        title?.let {
            holder.titleView.setText(it)
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var titleView: TextView

        override fun bindView(itemView: View) {
            titleView = itemView.findViewById(R.id.text)
        }
    }
}
