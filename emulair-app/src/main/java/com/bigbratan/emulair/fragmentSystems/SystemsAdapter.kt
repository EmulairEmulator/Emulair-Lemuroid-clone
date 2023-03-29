package com.bigbratan.emulair.fragmentSystems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bigbratan.emulair.utils.systems.SystemUtils
import com.bigbratan.emulair.common.metadataRetrograde.MetaSystemID

class SystemViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
    private var coverView: ImageView? = null
    private var textView: TextView? = null
    private var subtextView: TextView? = null

    init {
        coverView = itemView.findViewById(R.id.image)
        textView = itemView.findViewById(R.id.text)
        subtextView = itemView.findViewById(R.id.subtext)
    }

    fun bind(systemUtils: SystemUtils, onSystemClick: (MetaSystemID) -> Unit) {
        textView?.text = itemView.context.resources.getString(systemUtils.metaSystemId.titleResId)
        if (systemUtils.count == 1)
            subtextView?.text = itemView.context.getString(R.string.systems_subtitle_alt)
        else subtextView?.text = itemView.context.getString(
            R.string.systems_subtitle,
            systemUtils.count.toString()
        )
        coverView?.setImageResource(systemUtils.metaSystemId.imageResId)
        itemView.setOnClickListener { onSystemClick(systemUtils.metaSystemId) }
    }
}

class SystemsAdapter(
    private val onSystemClick: (MetaSystemID) -> Unit
) : ListAdapter<SystemUtils, SystemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemViewHolder {
        return SystemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_card_system, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SystemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onSystemClick) }
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
