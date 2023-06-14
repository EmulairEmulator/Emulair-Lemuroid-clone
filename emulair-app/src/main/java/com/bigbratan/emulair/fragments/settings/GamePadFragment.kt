package com.bigbratan.emulair.fragments.settings

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.InputDevice
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerInput.InputDeviceManager
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.utils.kotlin.NTuple2
import com.bigbratan.emulair.common.managerPreferences.SharedPreferencesHelper
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GamePadFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var gamePadPreferences: GamePadPreferences

    @Inject
    lateinit var inputDeviceManager: InputDeviceManager

    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore =
            SharedPreferencesHelper.getSharedPreferencesDataStore(requireContext())
        setPreferencesFromResource(R.xml.preference_empty, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchOnState(Lifecycle.State.CREATED) {
            val gamePadStatus = combine(
                inputDeviceManager.getGamePadsObservable(),
                inputDeviceManager.getEnabledInputsObservable(),
                ::NTuple2
            )

            gamePadStatus
                .distinctUntilChanged()
                .collect { (pads, enabledPads) -> addGamePads(pads, enabledPads) }
        }

        launchOnState(Lifecycle.State.RESUMED) {
            inputDeviceManager.getEnabledInputsObservable()
                .distinctUntilChanged()
                .collect { refreshGamePads(it) }
        }
    }

    private fun addGamePads(pads: List<InputDevice>, enabledPads: List<InputDevice>) {
        lifecycleScope.launch {
            preferenceScreen.removeAll()
            gamePadPreferences.addGamePadsPreferencesToScreen(
                requireActivity(),
                preferenceScreen,
                pads,
                enabledPads
            )
        }
    }

    private fun refreshGamePads(enabledGamePads: List<InputDevice>) {
        lifecycleScope.launch {
            gamePadPreferences.refreshGamePadsPreferencesToScreen(preferenceScreen, enabledGamePads)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            getString(R.string.pref_key_reset_gamepad_bindings) -> lifecycleScope.launch {
                handleResetBindings()
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    private suspend fun handleResetBindings() {
        inputDeviceManager.resetAllBindings()
        addGamePads(
            inputDeviceManager.getGamePadsObservable().first(),
            inputDeviceManager.getEnabledInputsObservable().first()
        )
    }

    @dagger.Module
    class Module
}
