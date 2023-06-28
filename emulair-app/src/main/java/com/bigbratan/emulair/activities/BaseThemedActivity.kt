package com.bigbratan.emulair.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managers.settings.SettingsManager
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

open class BaseThemedActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
    }

    private fun applyTheme() {
        val chosenTheme = when (runBlocking { settingsManager.appTheme() }) {
            "dark_theme" -> R.style.Theme_EmulairMaterialDark
            "light_theme" -> R.style.Theme_EmulairMaterialLight
            "amoled_theme" -> R.style.Theme_EmulairMaterialAMOLED
            "monet_dark_theme" -> R.style.Theme_EmulairMaterialYouDark
            "monet_light_theme" -> R.style.Theme_EmulairMaterialYouLight
            "monet_amoled_theme" -> R.style.Theme_EmulairMaterialYouAMOLED
            else -> R.style.Theme_EmulairMaterialDark
        }
        setTheme(chosenTheme)
        recreate()
        /*val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)*/
    }
}
