package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

fun composeString(inputString: String): String {
    val gameName = inputString.substring(0, 15)
    val startIndex = inputString.indexOf('_') + 1
    val endIndex = inputString.indexOf('.')
    val substring = inputString.substring(startIndex, endIndex)



    return gameName + " (...) " + substring
}

class ImageViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
    private var textView: TextView? = null
    var imageView: ImageView
    private var originalTitle: String? = null

    init {
        textView = itemView.findViewById(R.id.text)
        imageView = itemView.findViewById(R.id.image)
        imageView.setOnClickListener{
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {

                Toast.makeText(itemView.context, "You clicked on ${originalTitle}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun bind(photo: Bitmap?, text: String?)  {
        if (photo != null) {
            originalTitle = text
            Glide.with(itemView.context).load(photo).into(imageView)
            val textToShow = composeString(text.toString())
            textView?.text = textToShow


        }
    else {
            Glide.with(itemView.context).load(R.drawable.ic_launcher_background).into(imageView)
            textView?.text = ""
        }
    }
}



class ImageAdapter(
    /*private val context: Context,*/
    private val cloudList: List<CloudState>?,


) : RecyclerView.Adapter<ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_image, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cloudList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
         holder.bind(cloudList?.get(position)?.image, cloudList?.get(position)?.title)
    }
}
