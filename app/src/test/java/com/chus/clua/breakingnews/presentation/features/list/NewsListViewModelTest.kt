package com.chus.clua.breakingnews.presentation.features.list

import androidx.lifecycle.Observer
import com.chus.clua.breakingnews.domain.Either
import com.chus.clua.breakingnews.domain.mapper.NewsToNewListMapper
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.domain.usecase.GetTopHeadlinesByCountryAndCategoryUseCase
import com.chus.clua.breakingnews.presentation.models.NewsList
import com.chus.clua.breakingnews.presentation.models.NewsWrapper
import com.chus.clua.breakingnews.utils.BaseViewModelTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsListViewModelTest : BaseViewModelTest() {

    @Mock
    private lateinit var countryCategoryUseCase: GetTopHeadlinesByCountryAndCategoryUseCase

    @Mock
    private lateinit var mapper: NewsToNewListMapper

    @Mock
    private lateinit var observer: Observer<NewsWrapper>

    @Mock
    private lateinit var errorObserver: Observer<String>

    @Mock
    private lateinit var loadingObserver: Observer<Boolean>

    private lateinit var viewModel: NewsListViewModel

    @Before
    fun setUp() {
        viewModel =
            NewsListViewModel(countryCategoryUseCase, mapper)
        viewModel.error.observeForever(errorObserver)
        viewModel.newsWrapper.observeForever(observer)
        viewModel.loading.observeForever(loadingObserver)
    }

    @Test
    fun `WHEN NewsListViewModel load by Country THEN show a list of NewsList`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val country = Country.getDefault()
            val list = mock<List<News>>()
            val listMapped = mock<List<NewsList>>()
            whenever(countryCategoryUseCase(country, null)).thenReturn(Either.Right(list))
            whenever(mapper.mapFromDomainList(list)).thenReturn(listMapped)

            viewModel.load(country)

            verify(loadingObserver).onChanged(true)
            verify(observer).onChanged(NewsWrapper("${country.name} ", listMapped))
            verify(errorObserver, never()).onChanged(anyString())
            verify(loadingObserver).onChanged(false)
        }

    @Test
    fun `WHEN NewsListViewModel load by Country THEN show an error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val country = mock<Country>()
            val exception = Exception("any message")
            whenever(countryCategoryUseCase(country, null)).thenReturn(Either.Left(exception))

            viewModel.load(country)

            verify(loadingObserver).onChanged(true)
            verify(observer, never()).onChanged(any())
            verify(errorObserver).onChanged(exception.message)
            verify(loadingObserver).onChanged(false)
        }

    @Test
    fun `WHEN NewsListViewModel load by Category THEN show a list of NewsList`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val category = Category("name", "key")
            val list = mock<List<News>>()
            val listMapped = mock<List<NewsList>>()
            whenever(countryCategoryUseCase(null, category)).thenReturn(Either.Right(list))
            whenever(mapper.mapFromDomainList(list)).thenReturn(listMapped)

            viewModel.load(category = category)

            verify(loadingObserver).onChanged(true)
            verify(observer).onChanged(NewsWrapper(" ${category.name}", listMapped))
            verify(errorObserver, never()).onChanged(anyString())
            verify(loadingObserver).onChanged(false)
        }

    @Test
    fun `WHEN NewsListViewModel load by Category THEN show an error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val category = mock<Category>()
            val exception = Exception("any message")
            whenever(countryCategoryUseCase(null, category)).thenReturn(Either.Left(exception))

            viewModel.load(category = category)

            verify(loadingObserver).onChanged(true)
            verify(observer, never()).onChanged(any())
            verify(errorObserver).onChanged(exception.message)
            verify(loadingObserver).onChanged(false)
        }

    @Test
    fun `WHEN NewsListViewModel load by Country and Category THEN show a list of NewsList`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val category = Category("name", "key")
            val country = Country.getDefault()
            val list = mock<List<News>>()
            val listMapped = mock<List<NewsList>>()
            whenever(countryCategoryUseCase(country, category)).thenReturn(Either.Right(list))
            whenever(mapper.mapFromDomainList(list)).thenReturn(listMapped)

            viewModel.load(country, category)

            verify(loadingObserver).onChanged(true)
            verify(observer).onChanged(NewsWrapper("${country.name} ${category.name}", listMapped))
            verify(errorObserver, never()).onChanged(anyString())
            verify(loadingObserver).onChanged(false)
        }

    @Test
    fun `WHEN NewsListViewModel load by Country and Category THEN show an error`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val category = mock<Category>()
            val country = Country.getDefault()
            val exception = Exception("any message")
            whenever(countryCategoryUseCase(country, category)).thenReturn(Either.Left(exception))

            viewModel.load(country, category)

            verify(loadingObserver).onChanged(true)
            verify(observer, never()).onChanged(any())
            verify(errorObserver).onChanged(exception.message)
            verify(loadingObserver).onChanged(false)
        }

    @Test
    fun `WHEN NewsListViewModel load by Country null and Category null THEN show an empty list`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val list = mock<List<News>>()
            val listMapped = mock<List<NewsList>>()
            whenever(countryCategoryUseCase(null, null)).thenReturn(Either.Right(list))
            whenever(mapper.mapFromDomainList(list)).thenReturn(listMapped)

            viewModel.load(null, null)

            verify(loadingObserver).onChanged(true)
            verify(observer).onChanged(NewsWrapper(" ", listMapped))
            verify(errorObserver, never()).onChanged(anyString())
            verify(loadingObserver).onChanged(false)
        }


}