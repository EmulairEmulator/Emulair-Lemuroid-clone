package com.bigbratan.emulair.managers.coresLibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CoreUpdateBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        com.bigbratan.emulair.managers.coresLibrary.LibraryIndexScheduler.cancelCoreUpdate(
            context!!.applicationContext
        )
    }
}
