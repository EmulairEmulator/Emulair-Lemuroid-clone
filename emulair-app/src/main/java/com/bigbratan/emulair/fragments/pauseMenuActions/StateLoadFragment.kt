package com.bigbratan.emulair.fragments.pauseMenuActions

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.pauseMenu.PauseMenuContract
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.utils.preferences.DummyDataStore
import com.bigbratan.emulair.common.metadata.retrograde.SystemCoreConfig
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import com.bigbratan.emulair.common.managers.saves.StatesManager
import com.bigbratan.emulair.common.managers.saves.StatesPreviewManager
import dagger.android.support.AndroidSupportInjection
import java.security.InvalidParameterException
import javax.inject.Inject

class StateLoadFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var statesManager: StatesManager
    @Inject
    lateinit var statesPreviewManager: StatesPreviewManager

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = DummyDataStore
        addPreferencesFromResource(R.xml.preference_empty)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val extras = activity?.intent?.extras

        val game = extras?.getSerializable(PauseMenuContract.EXTRA_GAME) as Game?
            ?: throw InvalidParameterException("Missing EXTRA_GAME")

        val systemCoreConfig = extras?.getSerializable(PauseMenuContract.EXTRA_SYSTEM_CORE_CONFIG) as SystemCoreConfig?
            ?: throw InvalidParameterException("Missing EXTRA_SYSTEM_CORE_CONFIG")

        launchOnState(Lifecycle.State.CREATED) {
            setupLoadPreference(game, systemCoreConfig)
        }
    }

    private suspend fun setupLoadPreference(game: Game, systemCoreConfig: SystemCoreConfig) {
        val slotsInfo = statesManager.getSavedSlotsInfo(game, systemCoreConfig.coreID)

        slotsInfo.forEachIndexed { index, saveInfo ->
            val bitmap = PauseMenuPreferences.getSaveStateBitmap(
                requireContext(),
                statesPreviewManager,
                saveInfo,
                game,
                systemCoreConfig.coreID,
                index
            )

            PauseMenuPreferences.addLoadPreference(
                preferenceScreen,
                index,
                saveInfo,
                bitmap
            )
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return PauseMenuPreferences.onPreferenceTreeClicked(activity, preference)
    }

    @dagger.Module
    class Module
}
