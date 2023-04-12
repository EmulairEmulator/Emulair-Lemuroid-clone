package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R

class CloudStatesAdapter(private val photos: List<Bitmap>): RecyclerView.Adapter<CloudStatesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cloudstate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Bitmap) {
            itemView.findViewById<ImageView>(R.id.iv_image_cloud_state).setImageBitmap(photo)
            itemView.findViewById<TextView>(R.id.tv_cloud_state_name).text = "Cloud State Random"
        }
    }
}


