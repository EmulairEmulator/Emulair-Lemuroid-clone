package com.bigbratan.emulair.fragmentSettings

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.managerPreferences.SharedPreferencesHelper
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CoresSelectionFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var coresSelectionPreferences: CoresSelectionPreferences

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore =
            SharedPreferencesHelper.getSharedPreferencesDataStore(requireContext())
        setPreferencesFromResource(R.xml.preference_empty, rootKey)
        coresSelectionPreferences.addCoresSelectionPreferences(preferenceScreen)
    }

    @dagger.Module
    class Module
}
