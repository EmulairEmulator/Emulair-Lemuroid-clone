/*
 * RetrogradeApplicationModule.kt
 *
 * Copyright (C) 2017 Retrograde Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bigbratan.emulair

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.bigbratan.emulair.activities.game.GameActivity
import com.bigbratan.emulair.activities.pauseMenu.PauseMenuActivity
import com.bigbratan.emulair.fragments.settings.GamePadBindingActivity
import com.bigbratan.emulair.activities.main.MainActivity
import com.bigbratan.emulair.managers.settings.SettingsManager
import com.bigbratan.emulair.managers.covers.CoverLoader
import com.bigbratan.emulair.activities.game.GameLauncher
import com.bigbratan.emulair.managers.input.InputDeviceManager
import com.bigbratan.emulair.activities.main.GameLaunchTaskHandler
import com.bigbratan.emulair.managers.rumble.RumbleManager
import com.bigbratan.emulair.fragments.settings.BiosPreferences
import com.bigbratan.emulair.managers.input.ControllerConfigsManager
import com.bigbratan.emulair.fragments.settings.CoresSelectionPreferences
import com.bigbratan.emulair.managers.settings.StorageFrameworkPickerLauncher
import com.bigbratan.emulair.ext.managers.coresLibrary.CoreUpdaterImpl
import com.bigbratan.emulair.ext.managers.review.ReviewManager
import com.bigbratan.emulair.ext.managers.saveSync.SaveSyncManagerImpl
import com.bigbratan.emulair.common.managers.bios.BiosManager
import com.bigbratan.emulair.common.managers.coresLibrary.CoreUpdater
import com.bigbratan.emulair.common.managers.coresLibrary.CoreVariablesManager
import com.bigbratan.emulair.common.managers.coresLibrary.CoresSelection
import com.bigbratan.emulair.common.activities.game.GameLoader
import com.bigbratan.emulair.common.managers.injection.PerActivity
import com.bigbratan.emulair.common.managers.injection.PerApp
import com.bigbratan.emulair.common.metadata.retrograde.EmulairLibrary
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadata.retrograde.db.dao.GameSearchDao
import com.bigbratan.emulair.common.metadata.retrograde.db.dao.Migrations
import com.bigbratan.emulair.common.metadata.retrograde.GameMetadataProvider
import com.bigbratan.emulair.common.managers.preferences.SharedPreferencesHelper
import com.bigbratan.emulair.common.managers.saves.SavesCoherencyEngine
import com.bigbratan.emulair.common.managers.saves.SavesManager
import com.bigbratan.emulair.common.managers.saves.StatesManager
import com.bigbratan.emulair.common.managers.saves.StatesPreviewManager
import com.bigbratan.emulair.common.managers.saveSync.SaveSyncManager
import com.bigbratan.emulair.common.managers.storage.DirectoriesManager
import com.bigbratan.emulair.common.managers.storage.StorageProvider
import com.bigbratan.emulair.common.managers.storage.StorageProviderRegistry
import com.bigbratan.emulair.common.managers.storage.local.LocalStorageProvider
import com.bigbratan.emulair.common.managers.storage.local.StorageAccessFrameworkProvider
import com.bigbratan.emulair.metadata.libretro.LibretroDBMetadataProvider
import com.bigbratan.emulair.metadata.libretro.db.LibretroDBManager
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoSet
import java.io.InputStream
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import java.util.zip.ZipInputStream
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

@Module
abstract class EmulairApplicationModule {

    @Binds
    abstract fun context(app: EmulairApplication): Context

    @Binds
    abstract fun saveSyncManager(saveSyncManagerImpl: SaveSyncManagerImpl): SaveSyncManager

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivity.Module::class])
    abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun gameActivity(): GameActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [PauseMenuActivity.Module::class])
    abstract fun pauseMenuActivity(): PauseMenuActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun storageFrameworkPickerLauncher(): StorageFrameworkPickerLauncher

    @PerActivity
    @ContributesAndroidInjector(modules = [GamePadBindingActivity.Module::class])
    abstract fun gamePadBindingActivity(): GamePadBindingActivity

    @Module
    companion object {
        @Provides
        @PerApp
        @JvmStatic
        fun libretroDBManager(app: EmulairApplication) = LibretroDBManager(app)

        @Provides
        @PerApp
        @JvmStatic
        fun retrogradeDb(app: EmulairApplication) =
            Room.databaseBuilder(app, RetrogradeDatabase::class.java, RetrogradeDatabase.DB_NAME)
                .addCallback(GameSearchDao.CALLBACK)
                .addMigrations(GameSearchDao.MIGRATION, Migrations.VERSION_8_9)
                .fallbackToDestructiveMigration()
                .build()

        @Provides
        @PerApp
        @JvmStatic
        fun gameMetadataProvider(libretroDBManager: LibretroDBManager): GameMetadataProvider =
            LibretroDBMetadataProvider(libretroDBManager)

        @Provides
        @PerApp
        @IntoSet
        @JvmStatic
        fun localSAFStorageProvider(context: Context): StorageProvider =
            StorageAccessFrameworkProvider(context)

        @Provides
        @PerApp
        @IntoSet
        @JvmStatic
        fun localGameStorageProvider(
            context: Context,
            directoriesManager: DirectoriesManager
        ): StorageProvider =
            LocalStorageProvider(context, directoriesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun gameStorageProviderRegistry(
            context: Context,
            providers: Set<@JvmSuppressWildcards StorageProvider>
        ) =
            StorageProviderRegistry(context, providers)

        @Provides
        @PerApp
        @JvmStatic
        fun emulairLibrary(
            db: RetrogradeDatabase,
            storageProviderRegistry: Lazy<StorageProviderRegistry>,
            gameMetadataProvider: Lazy<GameMetadataProvider>,
            biosManager: BiosManager
        ) = EmulairLibrary(db, storageProviderRegistry, gameMetadataProvider, biosManager)

        @Provides
        @PerApp
        @JvmStatic
        fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

        @Provides
        @PerApp
        @JvmStatic
        fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://example.com")
            .addConverterFactory(
                object : Converter.Factory() {
                    override fun responseBodyConverter(
                        type: Type?,
                        annotations: Array<out Annotation>?,
                        retrofit: Retrofit?
                    ): Converter<ResponseBody, *>? {
                        if (type == ZipInputStream::class.java) {
                            return Converter<ResponseBody, ZipInputStream> { responseBody ->
                                ZipInputStream(responseBody.byteStream())
                            }
                        }
                        if (type == InputStream::class.java) {
                            return Converter<ResponseBody, InputStream> { responseBody ->
                                responseBody.byteStream()
                            }
                        }
                        return null
                    }
                }
            )
            .build()

        @Provides
        @PerApp
        @JvmStatic
        fun directoriesManager(context: Context) = DirectoriesManager(context)

        @Provides
        @PerApp
        @JvmStatic
        fun statesManager(directoriesManager: DirectoriesManager) = StatesManager(directoriesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun savesManager(directoriesManager: DirectoriesManager) = SavesManager(directoriesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun statesPreviewManager(directoriesManager: DirectoriesManager) =
            StatesPreviewManager(directoriesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun coreManager(
            directoriesManager: DirectoriesManager,
            retrofit: Retrofit
        ): CoreUpdater = CoreUpdaterImpl(directoriesManager, retrofit)

        @Provides
        @PerApp
        @JvmStatic
        fun coreVariablesManager(sharedPreferences: Lazy<SharedPreferences>) =
            CoreVariablesManager(sharedPreferences)

        @Provides
        @PerApp
        @JvmStatic
        fun gameLoader(
            emulairLibrary: EmulairLibrary,
            statesManager: StatesManager,
            savesManager: SavesManager,
            coreVariablesManager: CoreVariablesManager,
            retrogradeDatabase: RetrogradeDatabase,
            savesCoherencyEngine: SavesCoherencyEngine,
            directoriesManager: DirectoriesManager,
            biosManager: BiosManager
        ) = GameLoader(
            emulairLibrary,
            statesManager,
            savesManager,
            coreVariablesManager,
            retrogradeDatabase,
            savesCoherencyEngine,
            directoriesManager,
            biosManager
        )

        @Provides
        @PerApp
        @JvmStatic
        fun inputDeviceManager(context: Context, sharedPreferences: Lazy<SharedPreferences>) =
            InputDeviceManager(context, sharedPreferences)

        @Provides
        @PerApp
        @JvmStatic
        fun biosManager(directoriesManager: DirectoriesManager) =
            BiosManager(directoriesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun biosPreferences(biosManager: BiosManager) = BiosPreferences(biosManager)

        @Provides
        @PerApp
        @JvmStatic
        fun coresSelection(sharedPreferences: Lazy<SharedPreferences>) =
            CoresSelection(sharedPreferences)

        @Provides
        @PerApp
        @JvmStatic
        fun coreSelectionPreferences() = CoresSelectionPreferences()

        @Provides
        @PerApp
        @JvmStatic
        fun savesCoherencyEngine(savesManager: SavesManager, statesManager: StatesManager) =
            SavesCoherencyEngine(savesManager, statesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun saveSyncManagerImpl(
            context: Context,
            directoriesManager: DirectoriesManager
        ) = SaveSyncManagerImpl(context, directoriesManager)

        @Provides
        @PerApp
        @JvmStatic
        fun postGameHandler(retrogradeDatabase: RetrogradeDatabase) =
            GameLaunchTaskHandler(ReviewManager(), retrogradeDatabase)

        @Provides
        @PerApp
        @JvmStatic
        fun retroControllerManager(sharedPreferences: Lazy<SharedPreferences>) =
            ControllerConfigsManager(sharedPreferences)

        @Provides
        @PerApp
        @JvmStatic
        fun settingsManager(context: Context, sharedPreferences: Lazy<SharedPreferences>) =
            SettingsManager(context, sharedPreferences)

        @Provides
        @PerApp
        @JvmStatic
        fun sharedPreferences(context: Context) =
            SharedPreferencesHelper.getSharedPreferences(context)

        @Provides
        @PerApp
        @JvmStatic
        fun gameLauncher(
            coresSelection: CoresSelection,
            gameLaunchTaskHandler: GameLaunchTaskHandler
        ) =
            GameLauncher(coresSelection, gameLaunchTaskHandler)

        @Provides
        @PerApp
        @JvmStatic
        fun rumbleManager(
            context: Context,
            settingsManager: SettingsManager,
            inputDeviceManager: InputDeviceManager
        ) =
            RumbleManager(context, settingsManager, inputDeviceManager)

        @Provides
        @PerApp
        @JvmStatic
        fun coverLoader(
            context: Context
        ) = CoverLoader(context)
    }
}
