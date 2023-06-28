package com.bigbratan.emulair.activities.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.account.AccountActivity
import com.bigbratan.emulair.activities.game.BaseGameActivity
import com.bigbratan.emulair.activities.game.GameLauncher
import com.bigbratan.emulair.activities.info.InfoActivity
import com.bigbratan.emulair.common.activities.retrograde.RetrogradeAppCompatActivity
import com.bigbratan.emulair.common.managers.injection.PerActivity
import com.bigbratan.emulair.common.managers.injection.PerFragment
import com.bigbratan.emulair.common.managers.saveSync.SaveSyncManager
import com.bigbratan.emulair.common.managers.storage.DirectoriesManager
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.utils.coroutines.safeLaunch
import com.bigbratan.emulair.ext.managers.review.ReviewManager
import com.bigbratan.emulair.fragments.allGames.AllGamesFragment
import com.bigbratan.emulair.fragments.favoriteGames.FavoriteGamesFragment
import com.bigbratan.emulair.fragments.home.HomeFragment
import com.bigbratan.emulair.fragments.search.SearchFragment
import com.bigbratan.emulair.fragments.settings.*
import com.bigbratan.emulair.fragments.systemGames.SystemGamesFragment
import com.bigbratan.emulair.fragments.systems.SystemsFragment
import com.bigbratan.emulair.managers.coresLibrary.LibraryIndexScheduler
import com.bigbratan.emulair.managers.interaction.GameInteractor
import com.bigbratan.emulair.managers.input.InputDeviceManager
import com.bigbratan.emulair.managers.saveSync.SaveSyncWork
import com.bigbratan.emulair.managers.settings.SettingsInteractor
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import java.util.*
import javax.inject.Inject


@OptIn(DelicateCoroutinesApi::class)
class MainActivity : RetrogradeAppCompatActivity(), BusyActivity {

    @Inject
    lateinit var gameLaunchTaskHandler: GameLaunchTaskHandler

    @Inject
    lateinit var saveSyncManager: SaveSyncManager

    private val reviewManager = ReviewManager()

    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        // window.navigationBarColor = SurfaceColors.SURFACE_0.getColor(this)
        // window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        setContentView(R.layout.activity_main)
        initializeActivity()
    }

    override fun activity(): Activity = this
    override fun isBusy(): Boolean = mainViewModel?.displayProgress?.value ?: false

    private fun initializeActivity() {
        setSupportActionBar(findViewById(R.id.toolbar))
        // findViewById<Toolbar>(R.id.toolbar).setNavigationIcon(R.drawable.ic_top_info)
        // supportActionBar?.setDisplayShowTitleEnabled(false)

        GlobalScope.safeLaunch {
            reviewManager.initialize(applicationContext)
        }

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val bottomBarIds = setOf(
            R.id.main_home,
            R.id.main_systems,
            R.id.main_settings
        )
        val appBarConfigurationBottom = AppBarConfiguration(bottomBarIds)
        setupActionBarWithNavController(navController, appBarConfigurationBottom)
        bottomNavView.setupWithNavController(navController)

        val buttonInfo: ImageButton = findViewById(R.id.main_info)
        val buttonSearch: ImageButton = findViewById(R.id.main_search)
        val buttonAccount: ImageButton = findViewById(R.id.main_account)

        buttonInfo.setOnClickListener {
            val intent = Intent(this@MainActivity, InfoActivity::class.java)
            /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_left, 0)
            intent.putExtra("noTransition", true)
            startActivity(intent, options.toBundle())*/
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
            ActivityCompat.startActivity(this, intent, options.toBundle())
        }
        buttonAccount.setOnClickListener {
            val intent = Intent(this@MainActivity, AccountActivity::class.java)
            /*intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_right, 0)
            intent.putExtra("noTransition", true)
            startActivity(intent, options.toBundle())*/
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
            ActivityCompat.startActivity(this, intent, options.toBundle())
        }
        buttonSearch.setOnClickListener {
            navController.navigateUp()
            navController.navigate(R.id.main_search)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            buttonInfo.visibility =
                if (destination.id == R.id.main_search ||
                    destination.id == R.id.main_advanced ||
                    destination.id == R.id.main_bios ||
                    destination.id == R.id.main_all_games ||
                    destination.id == R.id.main_favorite_games ||
                    destination.id == R.id.main_cores_selection ||
                    destination.id == R.id.main_account ||
                    destination.id == R.id.main_gamepad ||
                    destination.id == R.id.main_save_sync ||
                    destination.id == R.id.main_system_games
                ) View.GONE
                else View.VISIBLE
            buttonSearch.visibility =
                if (destination.id == R.id.main_home) View.VISIBLE
                else View.GONE
            buttonAccount.visibility =
                if (destination.id == R.id.main_search) View.GONE
                else View.VISIBLE
        }

        val factory = MainViewModel.Factory(applicationContext)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        mainViewModel?.displayProgress?.observe(this) { isRunning ->
            findViewById<ProgressBar>(R.id.progress).isVisible = isRunning
        }

        LibraryIndexScheduler.scheduleLibrarySync(
            applicationContext
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            BaseGameActivity.REQUEST_PLAY_GAME -> {
                GlobalScope.safeLaunch {
                    gameLaunchTaskHandler.handleGameFinish(true, this@MainActivity, resultCode, data)
                }
            }
        }
    }

    override fun onResume(){    
        super.onResume()
        LibraryIndexScheduler.scheduleLibrarySync(
            applicationContext
        )
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val isSyncSupported = saveSyncManager.isSupported()
        val isSyncConfigured = saveSyncManager.isConfigured()
        menu.findItem(R.id.menu_options_sync)?.isVisible = isSyncSupported && isSyncConfigured
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_nav_top, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_options_sync -> {
                SaveSyncWork.enqueueManualWork(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    @dagger.Module
    abstract class Module {

        @PerFragment
        @ContributesAndroidInjector(modules = [SettingsFragment.Module::class])
        abstract fun settingsFragment(): SettingsFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [HomeFragment.Module::class])
        abstract fun homeFragment(): HomeFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [SearchFragment.Module::class])
        abstract fun searchFragment(): SearchFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [SystemsFragment.Module::class])
        abstract fun systemsFragment(): SystemsFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [SystemGamesFragment.Module::class])
        abstract fun systemGamesFragment(): SystemGamesFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [AllGamesFragment.Module::class])
        abstract fun allGamesFragment(): AllGamesFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [FavoriteGamesFragment.Module::class])
        abstract fun favoriteGamesFragment(): FavoriteGamesFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [GamePadFragment.Module::class])
        abstract fun gamePadFragment(): GamePadFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [BiosFragment.Module::class])
        abstract fun biosFragment(): BiosFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [AdvancedFragment.Module::class])
        abstract fun advancedFragment(): AdvancedFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [SaveSyncFragment.Module::class])
        abstract fun saveSyncFragment(): SaveSyncFragment

        @PerFragment
        @ContributesAndroidInjector(modules = [CoresSelectionFragment.Module::class])
        abstract fun coresSelectionFragment(): CoresSelectionFragment

        @dagger.Module
        companion object {

            @Provides
            @PerActivity
            @JvmStatic
            fun settingsInteractor(activity: MainActivity, directoriesManager: DirectoriesManager) =
                SettingsInteractor(activity, directoriesManager)

            @Provides
            @PerActivity
            @JvmStatic
            fun gamePadPreferencesHelper(inputDeviceManager: InputDeviceManager) =
                GamePadPreferences(inputDeviceManager)

            @Provides
            @PerActivity
            @JvmStatic
            fun gameInteractor(
                activity: MainActivity,
                retrogradeDb: RetrogradeDatabase,
                gameLauncher: GameLauncher
            ) =
                GameInteractor(activity, retrogradeDb, gameLauncher)
        }
    }
}
