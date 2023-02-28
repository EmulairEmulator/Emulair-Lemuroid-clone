package com.bigbratan.emulair.fragmentSystems

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerLayout.DynamicGridLayoutManager
import com.bigbratan.emulair.managerLayout.GridSpaceDecoration
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.metadataRetrograde.MetaSystemID
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SystemsFragment : Fragment() {

    @Inject
    lateinit var retrogradeDb: RetrogradeDatabase

    /*
    @Inject
    lateinit var onSystemClick: (MetaSystemID) -> Unit
    */

    private var systemsAdapter: SystemsAdapter? = null

    private lateinit var systemsViewModel: SystemsViewModel

    protected var recyclerView: RecyclerView? = null

    protected var emptyView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_systems, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        emptyView = root.findViewById(R.id.empty_view)
        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SystemsViewModel.Factory(retrogradeDb, requireContext().applicationContext)
        systemsViewModel = ViewModelProvider(this, factory)[SystemsViewModel::class.java]
        systemsAdapter = SystemsAdapter { navigateToSystemGames(it) }

        /*
        val pagingController = EpoxySystemsController(onSystemClick)
        launchOnState(Lifecycle.State.CREATED) {
            systemsViewModel.getViewStates().collect {
                pagingController.update(it)
                // emptyView?.isVisible = it.isEmpty()
            }
        }
        */

        launchOnState(Lifecycle.State.CREATED) {
            systemsViewModel.metaSystems.collect {
                systemsAdapter?.submitList(it)
                emptyView?.isVisible = it.isEmpty()
            }
        }

        recyclerView?.apply {
            this.adapter = systemsAdapter
            this.layoutManager = DynamicGridLayoutManager(context, 3)

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }
    }

    private fun navigateToSystemGames(system: MetaSystemID) {
        val dbNames = system.systemIDs
            .map { it.dbname }
            .toTypedArray()

        val action = SystemsFragmentDirections.actionMainSystemsToMainSystemGames(dbNames)
        findNavController().navigate(action)
    }

    @dagger.Module
    class Module
}
