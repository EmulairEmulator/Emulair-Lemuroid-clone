package com.bigbratan.emulair

import android.annotation.SuppressLint
import android.content.Context
import androidx.startup.AppInitializer
import androidx.work.ListenableWorker
import com.google.android.material.color.DynamicColors
import com.bigbratan.emulair.managers.startup.GameProcessInitializer
import com.bigbratan.emulair.managers.startup.MainProcessInitializer
import com.bigbratan.emulair.utils.android.isMainProcess
import com.bigbratan.emulair.ext.managers.context.ContextHandler
import com.bigbratan.emulair.common.managers.injection.HasWorkerInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class EmulairApplication : DaggerApplication(), HasWorkerInjector {

    @Inject
    lateinit var workerInjector: DispatchingAndroidInjector<ListenableWorker>

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        val initializeComponent = if (isMainProcess()) {
            MainProcessInitializer::class.java
        } else {
            GameProcessInitializer::class.java
        }

        AppInitializer.getInstance(this).initializeComponent(initializeComponent)

        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        ContextHandler.attachBaseContext(base)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerEmulairApplicationComponent.builder().create(this)
    }

    override fun workerInjector(): AndroidInjector<ListenableWorker> = workerInjector
}
