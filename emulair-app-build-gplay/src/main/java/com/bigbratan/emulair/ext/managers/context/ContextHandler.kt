package com.bigbratan.emulair.ext.managers.context

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

object ContextHandler {
    fun attachBaseContext(context: Context) {
        SplitCompat.install(context)
    }
}
