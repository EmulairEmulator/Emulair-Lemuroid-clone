package com.bigbratan.emulair.managerLayout

import androidx.recyclerview.widget.RecyclerView

class GridSpaceDecorationAlt {

    companion object {
        fun setSingleGridSpaceDecoration(recyclerView: RecyclerView, pixelSpacing: Int) {
            recyclerView.setPadding(pixelSpacing, pixelSpacing, pixelSpacing, 0)
            recyclerView.clipToPadding = false
        }
    }
}
