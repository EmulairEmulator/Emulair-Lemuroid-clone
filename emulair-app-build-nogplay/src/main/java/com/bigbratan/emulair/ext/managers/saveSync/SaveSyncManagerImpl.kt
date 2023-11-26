package com.bigbratan.emulair.ext.managers.saveSync

import android.app.Activity
import android.content.Context
import com.bigbratan.emulair.common.metadata.retrograde.CoreID
import com.bigbratan.emulair.common.managers.saveSync.SaveSyncManager
import com.bigbratan.emulair.common.managers.storage.DirectoriesManager
import android.net.Uri
import android.text.format.Formatter
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import com.bigbratan.emulair.common.utils.kotlin.SharedPreferencesDelegates
import com.bigbratan.emulair.common.managers.preferences.SharedPreferencesHelper
import timber.log.Timber
import java.io.*
import java.text.SimpleDateFormat 

class SaveSyncManagerImpl(
    private val appContext: Context,
    private val directoriesManager: DirectoriesManager
) : SaveSyncManager {

    private var lastSyncTimestamp: Long by SharedPreferencesDelegates.LongDelegate(
        SharedPreferencesHelper.getSharedPreferences(appContext),
        appContext.getString(R.string.pref_key_last_save_sync),
        0L
    )

    private var storageUri: String by SharedPreferencesDelegates.StringDelegate(
        SharedPreferencesHelper.getSharedPreferences(appContext),
        appContext.getString(R.string.pref_key_saf_uri),
        ActivateSAFActivity.PREF_KEY_STORAGE_URI_NONE
    )


    override fun getProvider(): String = appContext.getString(R.string.saf_save_sync_providername)

    override fun getSettingsActivity(): Class<out Activity>? = ActivateSAFActivity::class.java

    override fun isSupported(): Boolean = true

    override fun isConfigured(): Boolean {
        return storageUri != ActivateSAFActivity.PREF_KEY_STORAGE_URI_NONE
    }

    override fun getLastSyncInfo(): String {
        val dateString = if (lastSyncTimestamp > 0) {
            SimpleDateFormat.getDateTimeInstance().format(lastSyncTimestamp)
        } else {
            "-"
        }
        return appContext.getString(R.string.saf_last_sync_completed, dateString)
    }

    override fun getConfigInfo(): String {
        return storageUri
    }

    /**
     * Sync savegames.
     *
     * Todo: Sync states!
     */
    override suspend fun sync(cores: Set<CoreID>) {
        synchronized(SYNC_LOCK) {
            val saveSyncResult = runCatching {
                val safProviderUri = Uri.parse(storageUri)
                val safDirectory = DocumentFile.fromTreeUri(appContext.applicationContext, safProviderUri)

                if (safDirectory != null) {

                    // copy from saf to internal
                    updateInternalStorage(safDirectory, "saves")

                    // now copy from internal to saf
                    updateRemoteStorage(safDirectory, "saves")

                    //repeat for states
                    updateInternalStorage(safDirectory, "states")
                    updateRemoteStorage(safDirectory, "states")
                }
                lastSyncTimestamp = System.currentTimeMillis()
            }

            saveSyncResult.onFailure {
                Timber.e(it, "Error while performing save sync.")
            }
        }
    }

    private fun updateInternalStorage(safDirectory: DocumentFile, subdir: String) {
        val internalSaves = File(appContext.getExternalFilesDir(null), subdir)
        var safSubdir = safDirectory.findFile(subdir)
        if(safSubdir == null) {
            safSubdir = safDirectory.createDirectory(subdir)
        }

        if (safSubdir != null) {
            updateInternalStorageFolder(safSubdir, internalSaves)
        }
    }
}

