package com.bigbratan.emulair.managerCoresLibrary

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.bigbratan.emulair.managerNotifications.NotificationsManager
import com.bigbratan.emulair.common.managerInjection.AndroidWorkerInjection
import com.bigbratan.emulair.common.managerInjection.WorkerKey
import com.bigbratan.emulair.common.metadataRetrograde.EmulairLibrary
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class LibraryIndexWork(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var emulairLibrary: EmulairLibrary

    override suspend fun doWork(): Result {
        AndroidWorkerInjection.inject(this)

        val notificationsManager = NotificationsManager(applicationContext)

        val foregroundInfo = ForegroundInfo(
            NotificationsManager.LIBRARY_INDEXING_NOTIFICATION_ID,
            notificationsManager.libraryIndexingNotification()
        )

        setForegroundAsync(foregroundInfo)

        val result = withContext(Dispatchers.IO) {
            kotlin.runCatching {
                emulairLibrary.indexLibrary()
            }
        }

        result.exceptionOrNull()?.let {
            Timber.e("Library indexing work terminated with an exception:", it)
        }

        LibraryIndexScheduler.scheduleCoreUpdate(applicationContext)

        return Result.success()
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @WorkerKey(LibraryIndexWork::class)
        abstract fun bindMyWorkerFactory(builder: Subcomponent.Builder): AndroidInjector.Factory<out ListenableWorker>
    }

    @dagger.Subcomponent
    interface Subcomponent : AndroidInjector<LibraryIndexWork> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<LibraryIndexWork>()
    }
}
