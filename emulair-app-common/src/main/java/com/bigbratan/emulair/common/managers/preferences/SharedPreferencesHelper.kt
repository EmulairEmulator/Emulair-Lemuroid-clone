package com.bigbratan.emulair.common.managers.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.frybits.harmony.getHarmonySharedPreferences
import com.bigbratan.emulair.common.utils.preferences.SharedPreferencesDataStore
import com.bigbratan.emulair.common.R

object SharedPreferencesHelper {

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getHarmonySharedPreferences(context.getString(R.string.pref_file_harmony_options))
    }

    fun getSharedPreferencesDataStore(context: Context): SharedPreferencesDataStore {
        return SharedPreferencesDataStore(getSharedPreferences(context))
    }

    /*
    Default shared preferences does not work with multi-process. It's currently used only for
    stored directory which are only read in the main process.
    */
    @Deprecated("Uses standard preference manager. This is not supported in multi-processes.")
    fun getLegacySharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}
