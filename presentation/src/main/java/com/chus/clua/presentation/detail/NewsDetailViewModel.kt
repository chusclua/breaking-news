package com.chus.clua.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.fold
import com.chus.clua.domain.model.News
import com.chus.clua.domain.usecase.DeleteOnWatchListUseCase
import com.chus.clua.domain.usecase.GetNewsUseCase
import com.chus.clua.domain.usecase.IsOnWatchListUseCase
import com.chus.clua.domain.usecase.SaveToWatchListUseCase
import com.chus.clua.presentation.mappers.toNewsUI
import com.chus.clua.presentation.models.NewsUI
import com.chus.clua.presentation.navigation.Arguments
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getNewsUseCase: GetNewsUseCase,
    isOnWatchListUseCase: IsOnWatchListUseCase,
    private val saveToWatchListUseCase: SaveToWatchListUseCase,
    private val deleteOnWatchListUseCase: DeleteOnWatchListUseCase
) : ViewModel() {

    private val _newsFlow: MutableStateFlow<DetailViewState> by lazy {
        MutableStateFlow(DetailViewState())
    }
    val newsFlow: StateFlow<DetailViewState>
        get() = _newsFlow.asStateFlow()

    init {
        val url = savedStateHandle.get<String>(Arguments.URL).orEmpty()
        val decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
        getNewsUseCase(decodedUrl).fold(
            leftOp = ::onFailure,
            rightOp = ::onSuccess
        )
        viewModelScope.launch {
            val isOnWatchList = isOnWatchListUseCase(url)
            _newsFlow.update { it.copy(news = it.news?.copy(isOnWatchList = isOnWatchList)) }
        }
    }

    private fun onSuccess(news: News) {
        _newsFlow.update {
            it.copy(news = news.toNewsUI())
        }
    }

    private fun onFailure(exception: Exception) {
        _newsFlow.update {
            it.copy(error = true)
        }
    }

    fun toggleOnWatchList(news: NewsUI) {
        viewModelScope.launch {
            if (news.isOnWatchList) {
                deleteOnWatchListUseCase(news.url)
                _newsFlow.update { it.copy(news = it.news?.copy(isOnWatchList = false)) }
            } else {
                saveToWatchListUseCase(news.url)
                _newsFlow.update { it.copy(news = it.news?.copy(isOnWatchList = true)) }
            }
        }
    }

}