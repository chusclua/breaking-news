package com.chus.clua.breakingnews.domain.usecase

import com.chus.clua.breakingnews.domain.*
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.domain.repository.CountriesRepository
import com.chus.clua.breakingnews.domain.repository.NewsRepository
import com.chus.clua.breakingnews.utils.BaseTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class GetTopHeadlinesByCountryAndCategoryUseCaseTest: BaseTest() {

    @Mock
    private lateinit var repository: NewsRepository

    private lateinit var useCase: GetTopHeadlinesByCountryAndCategoryUseCase

    @Before
    fun setUp() {
        useCase = GetTopHeadlinesByCountryAndCategoryUseCase(repository)
    }

    @Test
    fun `WHEN GetTopHeadlinesByCountryAndCategoryUseCase is invoked THEN obtains an Either Right`() =
        testCoroutineDispatcher.runBlockingTest {
            val list = mock<List<News>>()
            val country = mock<Country>()
            val category = mock<Category>()
            whenever(repository.getTopHeadlinesByCountryAndCategory(country.code, category.key)).thenReturn(
                Either.Right(list)
            )
            val result = useCase(country, category)
            assertTrue(result.isRight)
            assertEquals(list, result.data)
        }

    @Test
    fun `WHEN GetTopHeadlinesByCountryAndCategoryUseCase is invoked THEN obtains an Either Left`() =
        testCoroutineDispatcher.runBlockingTest {
            val exception = mock<Exception>()
            val country = mock<Country>()
            val category = mock<Category>()
            whenever(repository.getTopHeadlinesByCountryAndCategory(country.code, category.key)).thenReturn(
                Either.Left(exception)
            )
            val result = useCase(country, category)
            assertTrue(result.isLeft)
            assertEquals(exception.message, result.error.message)
        }
}