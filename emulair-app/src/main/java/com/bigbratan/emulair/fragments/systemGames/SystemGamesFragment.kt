package com.bigbratan.emulair.fragments.systemGames

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.*
import com.bigbratan.emulair.managerCovers.CoverLoader
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.managerInteraction.GameInteractor
import com.bigbratan.emulair.managerLayout.DynamicGridLayoutManager
import com.bigbratan.emulair.managerLayout.GridSpaceDecoration
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SystemGamesFragment : Fragment() {

    @Inject
    lateinit var retrogradeDb: RetrogradeDatabase

    @Inject
    lateinit var gameInteractor: GameInteractor

    @Inject
    lateinit var coverLoader: CoverLoader

    private val args: SystemGamesFragmentArgs by navArgs()

    private lateinit var systemGamesViewModel: SystemGamesViewModel

    private var gamesAdapter: SystemGamesAdapter? = null

    protected var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_basic, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SystemGamesViewModel.Factory(retrogradeDb)
        systemGamesViewModel = ViewModelProvider(this, factory)[SystemGamesViewModel::class.java]
        gamesAdapter = SystemGamesAdapter(R.layout.layout_card_game_horz, gameInteractor, coverLoader)

        recyclerView?.apply {
            adapter = gamesAdapter
            layoutManager = DynamicGridLayoutManager(context, 3)

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }

        launchOnState(Lifecycle.State.RESUMED) {
            systemGamesViewModel.systemGames
                .collect { gamesAdapter?.submitData(lifecycle, it) }
        }

        systemGamesViewModel.systemIds.value = (listOf(*args.systemIds))
    }

    @dagger.Module
    class Module
}
