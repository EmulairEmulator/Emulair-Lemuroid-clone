package com.bigbratan.emulair.fragments.home

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bigbratan.emulair.R

@EpoxyModelClass(layout = R.layout.layout_card_notice)
abstract class EpoxyNoticeView : EpoxyModelWithHolder<EpoxyNoticeView.Holder>() {

    @EpoxyAttribute
    var text: Int? = null

    override fun bind(holder: Holder) {
        text?.let {
            holder.textView.setText(it)
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var textView: TextView

        override fun bindView(itemView: View) {
            textView = itemView.findViewById(R.id.text)
        }
    }
}
