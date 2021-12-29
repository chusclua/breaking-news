package com.chus.clua.breakingnews.presentation.features.filter

import androidx.lifecycle.*
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.usecase.GetAvailableCategoriesUseCase
import com.chus.clua.breakingnews.domain.usecase.GetAvailableCountriesUseCase
import com.chus.clua.breakingnews.presentation.models.Filter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFilterViewModel @Inject constructor(
    private val getAvailableCountriesUseCase: GetAvailableCountriesUseCase,
    private val getAvailableCategoriesUseCase: GetAvailableCategoriesUseCase
) : ViewModel() {

    private val _selectedCountry = MutableLiveData<Country>()
    val country: LiveData<Country> get() = _selectedCountry

    private val _selectedCategory = MutableLiveData<Category>()
    val category: LiveData<Category> get() = _selectedCategory

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun load() {
        viewModelScope.launch {
            _countries.postValue(getAvailableCountriesUseCase.invoke())
            _categories.postValue(getAvailableCategoriesUseCase.invoke())
        }
    }

    fun setCountry(country: Country) {
        _selectedCountry.postValue(country)
    }

    fun setCategory(category: Category) {
        _selectedCategory.postValue(category)
    }

    fun filter() = Filter(country.value, category.value)
}