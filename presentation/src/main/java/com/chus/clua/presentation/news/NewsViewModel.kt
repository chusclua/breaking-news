package com.chus.clua.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.chus.clua.domain.usecase.DeleteAllWatchListUseCase
import com.chus.clua.domain.usecase.DeleteOnWatchListUseCase
import com.chus.clua.domain.usecase.GetTopHeadLinesNewsUseCase
import com.chus.clua.domain.usecase.GetWatchListUseCase
import com.chus.clua.domain.usecase.SaveToWatchListUseCase
import com.chus.clua.presentation.mappers.toNewsUI
import com.chus.clua.presentation.models.Category
import com.chus.clua.presentation.models.NewsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadLinesNewsUseCase: GetTopHeadLinesNewsUseCase,
    private val getWatchListUseCase: GetWatchListUseCase,
    private val saveToWatchListUseCase: SaveToWatchListUseCase,
    private val deleteOnWatchListUseCase: DeleteOnWatchListUseCase,
    private val deleteAllWatchListUseCase: DeleteAllWatchListUseCase
) : ViewModel() {

    private val _newsFlow: MutableStateFlow<PagingData<NewsUI>> =
        MutableStateFlow(value = PagingData.empty())
    val newsFlow: StateFlow<PagingData<NewsUI>> get() = _newsFlow

    private val _viewStateFlow: MutableStateFlow<NewsViewState> =
        MutableStateFlow(NewsViewState())
    val viewStateFlow: StateFlow<NewsViewState> get() = _viewStateFlow

    init {
        viewModelScope.launch { getWatchList() }
        viewModelScope.launch { getTopHeadLinesNews() }
    }

    private suspend fun getTopHeadLinesNews() {
        getTopHeadLinesNewsUseCase(viewStateFlow.value.selectedCategory.key)
            .distinctUntilChanged()
            .cachedIn(viewModelScope).map { pagingData ->
                val watchList = _viewStateFlow.value.watchList
                pagingData.map {
                    it.toNewsUI().copy(isOnWatchList = it.url in watchList.map { it.url })
                }
            }
            .collect { data ->
                _newsFlow.value = data
            }
    }

    private suspend fun getWatchList() {
        getWatchListUseCase()
            .distinctUntilChanged()
            .collect { list ->
                _viewStateFlow.update {
                    it.copy(watchList = list.map { news ->
                        news.toNewsUI().copy(isOnWatchList = true)
                    })
                }
                _newsFlow.update {
                    _newsFlow.value.map { it.copy(isOnWatchList = it.url in list.map { it.url }) }
                }
            }
    }


    fun toggleOnWatchList(news: NewsUI) {
        viewModelScope.launch {
            if (news.isOnWatchList) {
                deleteOnWatchListUseCase(news.url)
            } else {
                saveToWatchListUseCase(news.url)
            }
        }
    }

    fun clearAllWatchList() {
        viewModelScope.launch {
            deleteAllWatchListUseCase()
        }
    }

    fun onIndexSelected(index: Int) {
        _viewStateFlow.update { it.copy(bottomBarIndex = index) }
    }

    fun onCategoryClick(category: Category) {
        _viewStateFlow.update { it.copy(selectedCategory = category) }
        viewModelScope.launch { getTopHeadLinesNews() }
    }
}