package com.chus.clua.breakingnews.data.repository

import com.chus.clua.breakingnews.data.mapper.ArticleToNewsMapper
import com.chus.clua.breakingnews.data.network.NewsClient
import com.chus.clua.breakingnews.domain.*
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.repository.NewsRepository
import com.chus.clua.breakingnews.utils.BaseTest
import com.chus.clua.breakingnews.utils.articleModel
import com.chus.clua.breakingnews.utils.articlesModel
import com.chus.clua.breakingnews.utils.news
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryImpTest: BaseTest() {

    private lateinit var repository: NewsRepository

    @Mock
    private lateinit var client: NewsClient

    @Mock
    private lateinit var mapper: ArticleToNewsMapper

    @Before
    fun setUp() {
        repository = NewsRepositoryImp(client, mapper, testCoroutineDispatcher)
    }

    @Test
    fun `WHEN getTopHeadlinesByCountryAndCategory THEN returns an Either Right`() =
        testCoroutineDispatcher.runBlockingTest {
            val country = mock<Country>()
            val category = mock<Category>()
            val remoteList = listOf(articleModel, articleModel)
            val domainList = listOf(news, news)
            whenever(client.getTopHeadlines(country.code, category.key)).thenReturn(
                Either.Right(articlesModel.copy(articles = remoteList))
            )
            whenever(mapper.mapFromRemoteList(remoteList)).thenReturn(domainList)

            val result = repository.getTopHeadlinesByCountryAndCategory(country.code, category.key)

            assertTrue(result.isRight)
            assertEquals(listOf(news, news), result.data)
        }

    @Test
    fun `WHEN getTopHeadlinesByCountryAndCategory THEN returns an Either Left`() =
        testCoroutineDispatcher.runBlockingTest {
            val country = mock<Country>()
            val category = mock<Category>()
            val exception = mock<Exception>()
            whenever(client.getTopHeadlines(country.code, category.key)).thenReturn(Either.Left(exception))

            val result = repository.getTopHeadlinesByCountryAndCategory(country.code, category.key)

            assertTrue(result.isLeft)
            assertEquals(exception.message, result.error.message)
        }

    @Test
    fun `WHEN getTopHeadlinesByCountryAndCategory THEN API returns a null data`() =
        testCoroutineDispatcher.runBlockingTest {
            val country = mock<Country>()
            val category = mock<Category>()
            whenever(client.getTopHeadlines(country.code, category.key)).thenReturn(
                Either.Right(articlesModel)
            )

            val result = repository.getTopHeadlinesByCountryAndCategory(country.code, category.key)

            assertTrue(result.isLeft)
            assertEquals("Empty body", result.error.message)
        }

}