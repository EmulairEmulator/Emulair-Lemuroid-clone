package com.bigbratan.emulair.managers.startup

import android.content.Context
import androidx.startup.Initializer
import androidx.work.WorkManagerInitializer
import com.bigbratan.emulair.managerCoresLibrary.LibraryIndexScheduler
import com.bigbratan.emulair.managerSaveSync.SaveSyncWork
import timber.log.Timber

class MainProcessInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.i("Requested initialization of main process tasks")
        SaveSyncWork.enqueueAutoWork(context, 0)
        LibraryIndexScheduler.scheduleCoreUpdate(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(WorkManagerInitializer::class.java, DebugInitializer::class.java)
    }
}
