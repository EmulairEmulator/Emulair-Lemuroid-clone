package com.bigbratan.emulair.fragmentSearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.bigbratan.emulair.common.utils.paging.buildFlowPaging
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(private val retrogradeDb: RetrogradeDatabase) : ViewModel() {

    class Factory(val retrogradeDb: RetrogradeDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(retrogradeDb) as T
        }
    }

    val queryString = MutableStateFlow("")

    val searchResults: Flow<PagingData<Game>> = queryString
        .flatMapLatest {
            buildFlowPaging(20) { retrogradeDb.gameSearchDao().search(it) }
        }
}
