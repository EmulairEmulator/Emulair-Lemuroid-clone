package com.bigbratan.emulair.activities.pauseMenu

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.activities.retrograde.RetrogradeAppCompatActivity
import com.bigbratan.emulair.common.managerInjection.PerFragment
import com.bigbratan.emulair.fragmentPauseMenuActions.CoreOptionsFragment
import com.bigbratan.emulair.fragmentPauseMenuActions.PauseMenuFragment
import com.bigbratan.emulair.fragmentPauseMenuActions.StateLoadFragment
import com.bigbratan.emulair.fragmentPauseMenuActions.StateSaveFragment
import dagger.android.ContributesAndroidInjector

class PauseMenuActivity : RetrogradeAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pausemenu)
        setSupportActionBar(findViewById(R.id.toolbar))

        val navController = findNavController(R.id.nav_host_fragment)
        navController.setGraph(R.navigation.navigation_pausemenu, intent.extras)

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    @dagger.Module
    abstract class Module {

        @PerFragment
        @ContributesAndroidInjector(modules = [CoreOptionsFragment.Module::class])
        abstract fun coreOptionsFragment(): CoreOptionsFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [PauseMenuFragment.Module::class])
        abstract fun pauseMenuFragment(): PauseMenuFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [StateLoadFragment.Module::class])
        abstract fun stateLoadFragment(): StateLoadFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [StateSaveFragment.Module::class])
        abstract fun stateSaveFragment(): StateSaveFragment
    }
}
