package com.bigbratan.emulair.fragmentCloudStates

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bumptech.glide.Glide

/*
class ImageAdapter(private val imageList: List<Bitmap>?) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = imageList?.get(position)
        holder.bind(image)
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image_cloud_state)
        private val textView: TextView = itemView.findViewById(R.id.tv_cloud_state_name)

        fun bind(photo: Bitmap?) {
            if (photo != null) {
                imageView.setImageBitmap(photo)
                textView.text = "Random"
            } else {
                imageView.setImageBitmap(null)
                textView.text = ""
            }
        }
    }
}
*/

class ImageViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
    private var textView: TextView? = null
    var imageView: ImageView

    init {
        textView = itemView.findViewById(R.id.text)
        imageView = itemView.findViewById(R.id.image)
    }

    fun bind(photo: Bitmap?) {
        if (photo != null) {
            imageView?.setImageBitmap(photo)
            textView?.text = "Random"
        } else {
            imageView?.setImageBitmap(null)
            textView?.text = ""
        }
    }
}

class ImageAdapter(
    /*private val context: Context,*/
    private val imageList: List<Bitmap>?
) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
         holder.bind(imageList?.get(position))
    }
}
