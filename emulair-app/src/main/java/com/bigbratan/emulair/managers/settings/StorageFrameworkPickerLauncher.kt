package com.bigbratan.emulair.managers.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.coresLibrary.LibraryIndexScheduler
import com.bigbratan.emulair.utils.android.displayErrorDialog
import com.bigbratan.emulair.activities.retrograde.RetrogradeActivity
import com.bigbratan.emulair.activities.retrograde.RetrogradeAppCompatActivity
import com.bigbratan.emulair.common.managers.preferences.SharedPreferencesHelper
import com.bigbratan.emulair.common.managers.storage.DirectoriesManager
import javax.inject.Inject

class StorageFrameworkPickerLauncher : RetrogradeAppCompatActivity() {

    @Inject
    lateinit var directoriesManager: DirectoriesManager

    override fun getActivityName(): String {
        return "StorageFrameworkPickerLauncher"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                this.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                this.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                this.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
                this.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            }
            try {
                startActivityForResult(intent, REQUEST_CODE_PICK_FOLDER)
            } catch (e: Exception) {
                showStorageAccessFrameworkNotSupportedDialog()
            }
        }
    }

    private fun showStorageAccessFrameworkNotSupportedDialog() {
        val message = getString(R.string.storage_saf_not_found_message, directoriesManager.getInternalRomsDirectory())
        val actionLabel = getString(R.string.button_ok)
        displayErrorDialog(message, actionLabel) { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == REQUEST_CODE_PICK_FOLDER && resultCode == Activity.RESULT_OK) {
            val sharedPreferences = SharedPreferencesHelper.getLegacySharedPreferences(this)
            val preferenceKey = getString(R.string.pref_key_external_folder)

            val currentValue: String? = sharedPreferences.getString(preferenceKey, null)
            val newValue = resultData?.data

            if (newValue != null && newValue.toString() != currentValue) {
                updatePersistableUris(newValue)

                sharedPreferences.edit().apply {
                    this.putString(preferenceKey, newValue.toString())
                    this.apply()
                }
            }

            startLibraryIndexWork()
        }
        finish()
    }

    private fun updatePersistableUris(uri: Uri) {
        contentResolver.persistedUriPermissions
            .filter { it.isReadPermission }
            .filter { it.uri != uri }
            .forEach {
                contentResolver.releasePersistableUriPermission(
                    it.uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }

        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    private fun startLibraryIndexWork() {
        LibraryIndexScheduler.scheduleLibrarySync(applicationContext)
    }

    companion object {
        private const val REQUEST_CODE_PICK_FOLDER = 1

        fun pickFolder(context: Context) {
            context.startActivity(Intent(context, StorageFrameworkPickerLauncher::class.java))
        }
    }
}
