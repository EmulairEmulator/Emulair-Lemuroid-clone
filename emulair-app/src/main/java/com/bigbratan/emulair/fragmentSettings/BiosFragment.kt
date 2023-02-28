package com.bigbratan.emulair.fragmentSettings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.utils.preferences.DummyDataStore
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class BiosFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var biosPreferences: BiosPreferences

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = DummyDataStore
        setPreferencesFromResource(R.xml.preference_empty, rootKey)
        biosPreferences.addBiosPreferences(preferenceScreen)
    }

    @dagger.Module
    class Module
}
