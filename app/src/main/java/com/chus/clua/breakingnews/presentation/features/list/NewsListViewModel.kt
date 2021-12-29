package com.chus.clua.breakingnews.presentation.features.list

import androidx.lifecycle.*
import com.chus.clua.breakingnews.domain.fold
import com.chus.clua.breakingnews.domain.mapper.NewsToNewListMapper
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.domain.usecase.GetTopHeadlinesByCountryAndCategoryUseCase
import com.chus.clua.breakingnews.presentation.models.NewsWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getTopHeadlinesByCountryAndCategoryUseCase: GetTopHeadlinesByCountryAndCategoryUseCase,
    private val mapper: NewsToNewListMapper
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _countryNews = MutableLiveData<NewsWrapper>()
    val newsWrapper: LiveData<NewsWrapper> = _countryNews

    fun load(country: Country? = null, category: Category? = null) {
        viewModelScope.launch {
            _loading.postValue(true)
            getTopHeadlinesByCountryAndCategoryUseCase(country, category).fold(
                leftOp = { exception ->
                    _loading.postValue(false)
                    _error.postValue(exception.message)
                },
                rightOp = { list ->
                    _loading.postValue(false)
                    _countryNews.postValue(mapNews(country, category, list))
                }
            )
        }
    }

    private fun mapNews(country: Country?, category: Category? = null, list: List<News>) =
        NewsWrapper(
            "${country?.name ?: ""} ${category?.name ?: ""}",
            mapper.mapFromDomainList(list)
        )

}