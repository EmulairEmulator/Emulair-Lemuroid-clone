package com.bigbratan.emulair.fragments.pauseMenuActions

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.activities.pauseMenu.PauseMenuContract
import com.bigbratan.emulair.managerInput.InputDeviceManager
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.metadata.retrograde.SystemCoreConfig
import com.bigbratan.emulair.common.metadata.retrograde.db.entity.Game
import com.bigbratan.emulair.common.managerPreferences.SharedPreferencesHelper
import dagger.android.support.AndroidSupportInjection
import java.security.InvalidParameterException
import javax.inject.Inject

class CoreOptionsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var inputDeviceManager: InputDeviceManager

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore =
            SharedPreferencesHelper.getSharedPreferencesDataStore(requireContext())
        addPreferencesFromResource(R.xml.preference_empty)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchOnState(Lifecycle.State.CREATED) {
            inputDeviceManager
                .getGamePadsObservable()
                .collect { updateScreen(it.size) }
        }
    }

    private fun updateScreen(connectedGamePads: Int) {
        preferenceScreen.removeAll()

        val extras = activity?.intent?.extras

        val coreOptions = extras?.getSerializable(PauseMenuContract.EXTRA_CORE_OPTIONS) as Array<EmulairCoreOption>?
            ?: throw InvalidParameterException("Missing EXTRA_CORE_OPTIONS")

        val advancedCoreOptions = extras?.getSerializable(
            PauseMenuContract.EXTRA_ADVANCED_CORE_OPTIONS
        ) as Array<EmulairCoreOption>?
            ?: throw InvalidParameterException("Missing EXTRA_ADVANCED_CORE_OPTIONS")

        val game = extras?.getSerializable(PauseMenuContract.EXTRA_GAME) as Game?
            ?: throw InvalidParameterException("Missing EXTRA_GAME")

        val coreConfig = extras?.getSerializable(PauseMenuContract.EXTRA_SYSTEM_CORE_CONFIG) as SystemCoreConfig?
            ?: throw InvalidParameterException("Missing EXTRA_SYSTEM_CORE_CONFIG")

        CoreOptionsPreferences.addPreferences(
            preferenceScreen,
            game.systemId,
            coreOptions.toList(),
            advancedCoreOptions.toList()
        )

        CoreOptionsPreferences.addControllers(
            preferenceScreen,
            game.systemId,
            coreConfig.coreID,
            maxOf(1, connectedGamePads),
            coreConfig.controllerConfigs
        )
    }

    @dagger.Module
    class Module
}
