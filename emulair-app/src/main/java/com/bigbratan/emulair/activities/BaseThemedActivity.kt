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

open abstract class BaseThemedActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsManager: SettingsManager

    abstract fun getActivityName(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        applyTheme()
        super.onCreate(savedInstanceState)
        CustomMaterialCardView.baseThemedActivity = this
    }

    private fun applyTheme() {
        val chosenTheme = when (runBlocking { settingsManager.appTheme() }) {
            "dark_theme" -> setDarkTheme()
            "light_theme" -> setLightTheme()
            "amoled_theme" -> setAMOLEDTheme()
            else -> setDarkTheme()
        }
        setTheme(chosenTheme)
    }

    private fun setDarkTheme(): Int {
        return if (getActivityName() == "PauseMenuActivity") {
            R.style.Theme_EmulairMaterialYouDark_Menu
        } else if (getActivityName() == "GameActivity" || getActivityName() == "GameCrashActivity") {
            R.style.Theme_EmulairMaterialYouDark_Game
        } else if (getActivityName() == "GamePadBindingActivity" || getActivityName() == "StorageFrameworkPickerLauncher") {
            R.style.Theme_EmulairMaterialYouDark_Invisible
        } else {
            R.style.Theme_EmulairMaterialYouDark
        }
    }

    private fun setLightTheme(): Int {
        return if (getActivityName() == "PauseMenuActivity") {
            R.style.Theme_EmulairMaterialYouLight_Menu
        } else if (getActivityName() == "GameActivity" || getActivityName() == "GameCrashActivity") {
            R.style.Theme_EmulairMaterialYouLight_Game
        } else if (getActivityName() == "GamePadBindingActivity" || getActivityName() == "StorageFrameworkPickerLauncher") {
            R.style.Theme_EmulairMaterialYouLight_Invisible
        } else {
            R.style.Theme_EmulairMaterialYouLight
        }
    }

    private fun setAMOLEDTheme(): Int {
        return if (getActivityName() == "PauseMenuActivity") {
            R.style.Theme_EmulairMaterialYouAMOLED_Menu
        } else if (getActivityName() == "GameActivity" || getActivityName() == "GameCrashActivity") {
            R.style.Theme_EmulairMaterialYouAMOLED_Game
        } else if (getActivityName() == "GamePadBindingActivity" || getActivityName() == "StorageFrameworkPickerLauncher") {
            R.style.Theme_EmulairMaterialYouAMOLED_Invisible
        } else {
            R.style.Theme_EmulairMaterialYouAMOLED
        }
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
