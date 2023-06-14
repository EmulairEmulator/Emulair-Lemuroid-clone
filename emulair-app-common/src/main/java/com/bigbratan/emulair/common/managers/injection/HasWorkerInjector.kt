package com.bigbratan.emulair.common.managers.injection

import androidx.work.ListenableWorker
import dagger.android.AndroidInjector

interface HasWorkerInjector {
    fun workerInjector(): AndroidInjector<ListenableWorker>
}
