package com.swordfish.lemuroid.ext.feature.savesync

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.swordfish.lemuroid.ext.R
import com.swordfish.lemuroid.lib.preferences.SharedPreferencesHelper

class ActivateSAFActivity : AppCompatActivity() {

    companion object {
        const val PREF_KEY_STORAGE_URI_NONE = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
    }

    private fun openPicker() {

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            
        }

        resultLauncher.launch(intent)
    }

    private fun setStorageUri(uri: String) {
        
    }

    private fun updatePersistableUris(uri: Uri) {
        
    }

}

