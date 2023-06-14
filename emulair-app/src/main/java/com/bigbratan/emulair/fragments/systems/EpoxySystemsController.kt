/*
package com.bigbratan.emulair.fragmentSystems

import com.airbnb.epoxy.AsyncEpoxyController
import com.bigbratan.emulair.R
import com.bigbratan.emulair.BuildConfig
import com.bigbratan.emulair.common.metadataRetrograde.MetaSystemID
import com.bigbratan.emulair.utils.systems.SystemUtils

class EpoxySystemsController(
    private val onSystemClick: (MetaSystemID) -> Unit
) : AsyncEpoxyController() {

    private var uiState = SystemsViewModel.UIState()

    fun update(viewState: SystemsViewModel.UIState) {
        uiState = viewState
        requestModelBuild()
    }

    override fun buildModels() {
        if (displayStarredSystems()) {
            addCard(uiState.starredSystems)
        }
        if (displayNonStarredSystems()) {
            addCard(uiState.nonStarredSystems)
        }
    }

    private fun displayStarredSystems() = uiState.starredSystems.isNotEmpty()
    private fun displayNonStarredSystems() = uiState.nonStarredSystems.isNotEmpty()

    private fun addCard(systems: List<SystemUtils>) {
        systems.forEach { item ->
            epoxySystemView {
                systemUtils(item)
                onSystemClick(this@EpoxySystemsController.onSystemClick)
            }
        }
    }

    init {
        if (BuildConfig.DEBUG) {
            isDebugLoggingEnabled = true
        }
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }
}
*/
