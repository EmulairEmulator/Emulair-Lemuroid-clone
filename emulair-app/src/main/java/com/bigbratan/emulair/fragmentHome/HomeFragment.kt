package com.bigbratan.emulair.fragmentHome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerCovers.CoverLoader
import com.bigbratan.emulair.managerSettings.SettingsInteractor
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.managerInteraction.GameInteractor
import com.bigbratan.emulair.managerLayout.GridSpaceDecorationAlt
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var retrogradeDb: RetrogradeDatabase

    @Inject
    lateinit var gameInteractor: GameInteractor

    @Inject
    lateinit var coverLoader: CoverLoader

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = HomeViewModel.Factory(requireContext().applicationContext, retrogradeDb)
        val homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        val pagingController = EpoxyHomeController(gameInteractor, settingsInteractor, coverLoader)

        Carousel.setDefaultGlobalSnapHelperFactory(null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.home_recyclerview)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        GridSpaceDecorationAlt.setSingleGridSpaceDecoration(recyclerView, spacingInPixels)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pagingController.adapter

        launchOnState(Lifecycle.State.RESUMED) {
            homeViewModel.getViewStates().collect {
                pagingController.update(it)
            }
        }
    }

    fun navigateToAllGames() {
        val action = HomeFragmentDirections.actionMainHomeToMainAllGames()
        findNavController().navigate(action)
    }

    fun navigateToFavoriteGames() {
        val action = HomeFragmentDirections.actionMainHomeToMainFavoriteGames()
        findNavController().navigate(action)
    }

    @dagger.Module
    class Module
}
