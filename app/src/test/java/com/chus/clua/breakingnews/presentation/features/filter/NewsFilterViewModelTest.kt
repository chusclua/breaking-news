package com.chus.clua.breakingnews.presentation.features.filter

import androidx.lifecycle.Observer
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.usecase.GetAvailableCategoriesUseCase
import com.chus.clua.breakingnews.domain.usecase.GetAvailableCountriesUseCase
import com.chus.clua.breakingnews.utils.BaseViewModelTest
import com.chus.clua.breakingnews.utils.category
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsFilterViewModelTest : BaseViewModelTest() {

    @Mock
    private lateinit var countriesUseCase: GetAvailableCountriesUseCase

    @Mock
    private lateinit var categoriesUseCase: GetAvailableCategoriesUseCase

    private lateinit var viewModel: NewsFilterViewModel

    @Mock
    private lateinit var countriesObserver: Observer<List<Country>>

    @Mock
    private lateinit var categoriesObserver: Observer<List<Category>>

    @Mock
    private lateinit var countryObserver: Observer<Country>

    @Mock
    private lateinit var categoryObserver: Observer<Category>

    @Before
    fun setUp() {
        viewModel = NewsFilterViewModel(countriesUseCase, categoriesUseCase)
        viewModel.countries.observeForever(countriesObserver)
        viewModel.categories.observeForever(categoriesObserver)
        viewModel.country.observeForever(countryObserver)
        viewModel.category.observeForever(categoryObserver)
    }

    @Test
    fun `WHEN NewsListViewModel load THEN obtains a sorted list of countries and categories`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val cList = mock<List<Country>>()
            val caList = mock<List<Category>>()
            whenever(countriesUseCase()).thenReturn(cList)
            whenever(categoriesUseCase()).thenReturn(caList)

            viewModel.load()
            verify(countriesObserver).onChanged(cList)
            verify(categoriesObserver).onChanged(caList)
        }

    @Test
    fun `WHEN NewsListViewModel setCountry THEN country observed changed`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val country = Country.getDefault()
            viewModel.setCountry(country)
            verify(countryObserver).onChanged(country)
        }

    @Test
    fun `WHEN NewsListViewModel setCategory THEN category observed changed`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.setCategory(category)
            verify(categoryObserver).onChanged(category)
        }

}