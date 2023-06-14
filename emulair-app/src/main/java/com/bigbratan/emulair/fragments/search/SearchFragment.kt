package com.bigbratan.emulair.fragments.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bigbratan.emulair.common.metadata.retrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.utils.kotlin.lazySequenceOf
import com.bigbratan.emulair.managerCovers.CoverLoader
import com.bigbratan.emulair.managerInteraction.GameInteractor
import com.bigbratan.emulair.managerLayout.DynamicGridLayoutManager
import com.bigbratan.emulair.managerLayout.GridSpaceDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var retrogradeDb: RetrogradeDatabase

    @Inject
    lateinit var gameInteractor: GameInteractor

    @Inject
    lateinit var coverLoader: CoverLoader

    private lateinit var searchViewModel: SearchViewModel

    private val searchDebounce = MutableStateFlow("")

    private var recyclerView: RecyclerView? = null

    private var emptyView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        emptyView = root.findViewById(R.id.empty_view)
        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = SearchViewModel.Factory(retrogradeDb)
        searchViewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]
        val gamesAdapter = SearchedGamesAdapter(R.layout.layout_card_game_horz, gameInteractor, coverLoader)
        initializeMenuProvider()

        launchOnState(Lifecycle.State.RESUMED) {
            searchViewModel.searchResults
                .collect { gamesAdapter.submitData(viewLifecycleOwner.lifecycle, it) }
        }

        launchOnState(Lifecycle.State.RESUMED) {
            searchDebounce.debounce(1000)
                .collect { searchViewModel.queryString.value = it }
        }

        gamesAdapter.addLoadStateListener { loadState ->
            updateEmptyViewVisibility(loadState, gamesAdapter.itemCount)
        }

        recyclerView?.apply {
            this.adapter = gamesAdapter
            this.layoutManager = DynamicGridLayoutManager(context, 3)

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }
    }

    /*fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
        val params = (layoutParams as? ViewGroup.MarginLayoutParams)
        params?.setMargins(
            left ?: params.leftMargin,
            top ?: params.topMargin,
            right ?: params.rightMargin,
            bottom ?: params.bottomMargin)
        layoutParams = params
    }*/

    private fun initializeMenuProvider() {
        val menuHost: MenuHost = requireActivity()
        val menuProvider = object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

            override fun onMenuClosed(menu: Menu) {}

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem = menu.findItem(R.id.action_search)
                setupSearchMenuItem(searchItem)
            }
        }
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupSearchMenuItem(searchItem: MenuItem) {
        val onExpandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                activity?.onBackPressedDispatcher?.onBackPressed()
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem) = true
        }
        searchItem.setOnActionExpandListener(onExpandListener)

        searchItem.expandActionView()

        val searchView = searchItem.actionView as SearchView
        /*fun View.setMargin(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
            val params = (layoutParams as? ViewGroup.MarginLayoutParams)
            params?.setMargins(
                left ?: params.leftMargin,
                top ?: params.topMargin,
                right ?: params.rightMargin,
                bottom ?: params.bottomMargin)
            layoutParams = params
        }
        val dpRatio = requireContext().resources.displayMetrics.density
        val dpAsPixels = (60 * dpRatio).toInt()
        searchView.setMargin(0, 0, dpAsPixels, 0)*/
        searchView.maxWidth = Integer.MAX_VALUE

        val onQueryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchDebounce.value = newText
                return true
            }
        }
        searchView.setOnQueryTextListener(onQueryTextListener)
        searchView.setQuery(searchViewModel.queryString.value, false)
    }

    private fun updateEmptyViewVisibility(loadState: CombinedLoadStates, itemCount: Int) {
        val emptyViewConditions = lazySequenceOf(
            { loadState.source.refresh is LoadState.NotLoading },
            { loadState.append.endOfPaginationReached },
            { itemCount == 0 }
        )

        val emptyViewVisible = emptyViewConditions.all { it }

        recyclerView?.isVisible = !emptyViewVisible
        emptyView?.isVisible = emptyViewVisible
    }

    @dagger.Module
    class Module
}
