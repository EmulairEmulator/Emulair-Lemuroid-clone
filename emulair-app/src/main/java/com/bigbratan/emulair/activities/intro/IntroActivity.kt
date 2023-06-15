package com.bigbratan.emulair.activities.intro
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.main.MainActivity
import com.bigbratan.emulair.fragments.intro.IntroFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch
class IntroPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        IntroFragment(),
        IntroFragment(),
        IntroFragment(),
        IntroFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}


class IntroActivity : AppCompatActivity() {

    private lateinit var dataStore: DataStore<java.util.prefs.Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Create the data store for preferences
        dataStore = createDataStore(
            name = "intro_prefs",
            migrations = listOf(SharedPreferencesMigration(this, "intro_prefs"))
        )

        // Check if the intro was completed previously
        val introCompleted = readIntroCompleted()

        if (introCompleted) {
            startMainActivity()
        } else {
            setupIntro()
        }
    }

    private fun setupIntro() {
        // Setup the intro fragments and buttons
        // ...
    }

    private fun readIntroCompleted(): Boolean {
        val introCompletedKey = preferencesKey<Boolean>("intro_completed")
        val preferences = dataStore.data.first()
        return preferences[introCompletedKey] ?: false
    }

    private fun writeIntroCompleted(completed: Boolean) {
        val introCompletedKey = preferencesKey<Boolean>("intro_completed")
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[introCompletedKey] = completed
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
