package com.bigbratan.emulair.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.settings.SettingsManager
import com.bigbratan.emulair.ui.CustomMaterialCardView
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

abstract class BaseThemedActivity : AppCompatActivity() {
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
            "system_theme" -> {
                if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) setDarkTheme()
                else setLightTheme()
            }
            else -> setDarkTheme()
        }
        setTheme(chosenTheme)
    }

    private fun setDarkTheme(): Int {
        return if (getActivityName() == "PauseMenuActivity") {
            R.style.Theme_Dark_Menu
        } else if (getActivityName() == "GameActivity" || getActivityName() == "GameCrashActivity") {
            R.style.Theme_Dark_Game
        } else if (getActivityName() == "GamePadBindingActivity" || getActivityName() == "StorageFrameworkPickerLauncher") {
            R.style.Theme_Dark_Invisible
        } else {
            R.style.Theme_Dark
        }
    }

    private fun setLightTheme(): Int {
        return if (getActivityName() == "PauseMenuActivity") {
            R.style.Theme_Light_Menu
        } else if (getActivityName() == "GameActivity" || getActivityName() == "GameCrashActivity") {
            R.style.Theme_Dark_Game
        } else if (getActivityName() == "GamePadBindingActivity" || getActivityName() == "StorageFrameworkPickerLauncher") {
            R.style.Theme_Light_Invisible
        } else {
            R.style.Theme_Light
        }
    }

    fun adjustLuminance(luminance: Float): Float {
        return when (runBlocking { settingsManager.appTheme() }) {
            "dark_theme" -> luminance * 0.5f
            "light_theme" -> luminance * 1.05f
            "system_theme" -> {
                if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) luminance * 0.5f
                else luminance * 1.05f
            }
            else -> luminance * 0.5f
        }
    }
}
