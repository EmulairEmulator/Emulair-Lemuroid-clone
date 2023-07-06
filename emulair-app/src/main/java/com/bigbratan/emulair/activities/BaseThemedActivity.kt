package com.bigbratan.emulair.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.settings.SettingsManager
import com.bigbratan.emulair.ui.CustomListPreference
import com.bigbratan.emulair.ui.CustomMaterialCardView
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

open class BaseThemedActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        CustomMaterialCardView.baseThemedActivity = this
    }

    private fun applyTheme() {
        val chosenTheme = when (runBlocking { settingsManager.appTheme() }) {
            "dark_theme" -> R.style.Theme_EmulairMaterialYouDark
            "light_theme" -> R.style.Theme_EmulairMaterialYouLight
            "amoled_theme" -> R.style.Theme_EmulairMaterialYouAMOLED
            else -> R.style.Theme_EmulairMaterialYouDark
        }
        setTheme(chosenTheme)
    }

    fun adjustLuminance(luminance: Float): Float {
        return when (runBlocking { settingsManager.appTheme() }) {
            "dark_theme" -> luminance * 0.5f
            "light_theme" -> luminance * 1.05f
            "amoled_theme" -> luminance * 0.5f
            else -> luminance * 0.5f
        }
    }
}
