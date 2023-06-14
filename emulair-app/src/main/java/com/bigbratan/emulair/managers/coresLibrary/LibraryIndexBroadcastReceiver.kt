package com.bigbratan.emulair.managers.coresLibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LibraryIndexBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        LibraryIndexScheduler.cancelLibrarySync(context!!.applicationContext)
    }
}
