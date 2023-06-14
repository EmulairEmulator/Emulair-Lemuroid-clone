package com.bigbratan.emulair.managers.layout

import androidx.recyclerview.widget.RecyclerView

class GridSpaceDecorationAlt {

    companion object {
        fun setSingleGridSpaceDecoration(recyclerView: RecyclerView, pixelSpacing: Int) {
            recyclerView.setPadding(0, pixelSpacing, 0, pixelSpacing)
            recyclerView.clipToPadding = false
        }
    }
}
