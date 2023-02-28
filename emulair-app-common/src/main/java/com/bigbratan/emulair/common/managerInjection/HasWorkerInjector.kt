package com.bigbratan.emulair.common.managerInjection

import androidx.work.ListenableWorker
import dagger.android.AndroidInjector

interface HasWorkerInjector {
    fun workerInjector(): AndroidInjector<ListenableWorker>
}
