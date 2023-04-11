package com.bigbratan.emulair

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.startup.AppInitializer
import androidx.work.ListenableWorker
import com.google.android.material.color.DynamicColors
import com.bigbratan.emulair.managerStartup.GameProcessInitializer
import com.bigbratan.emulair.managerStartup.MainProcessInitializer
import com.bigbratan.emulair.utils.android.isMainProcess
import com.bigbratan.emulair.ext.managerContext.ContextHandler
import com.bigbratan.emulair.common.managerInjection.HasWorkerInjector
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import java.io.File
import javax.inject.Inject

class EmulairApplication : DaggerApplication(), HasWorkerInjector {

    @Inject
    lateinit var workerInjector: DispatchingAndroidInjector<ListenableWorker>

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        // Get a reference to the Firebase Storage instance
        val storage = FirebaseStorage.getInstance()
//
//// Create a reference to the file you want to download
//        val storageRef = storage.getReference("States/1986_-_Pokemon_Emerald_(U)(TrashMan).gba.slot2[1].jpg")
//
//// Download the file bytes
//        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
//            Toast.makeText(this, "Downloaded" + bytes.size, Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener { exception ->
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
//        }


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
