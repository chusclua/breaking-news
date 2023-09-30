package com.chus.clua.data.repository

import androidx.paging.testing.asSnapshot
import com.chus.clua.data.datasource.CacheDataSource
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.db.NewsDao
import com.chus.clua.data.utils.ArticlesModelTest
import com.chus.clua.data.utils.NewsEntityTest
import com.chus.clua.data.utils.NewsTest
import com.chus.clua.domain.Either
import com.chus.clua.domain.data
import com.chus.clua.domain.error
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.repository.NewsRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NewsRepositoryImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var newsService: RemoteDataSource

    @MockK
    lateinit var cacheDataSource: CacheDataSource

    @MockK
    lateinit var newsDao: NewsDao

    @Inject
    lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        repository = NewsRepositoryImp(newsService, cacheDataSource, newsDao)
    }

    @Test
    fun `given a success response when repo getTopHeadLinesByCategoryNews then returns a correct page`() =
        runTest {
            every { cacheDataSource.addNews(any()) } just Runs
            coEvery { newsService.getTopHeadLinesNews(page = 1, category = "sports") } returns Either.Right(ArticlesModelTest)
            coEvery { newsService.getTopHeadLinesNews(page = 2, category = "sports") } returns Either.Right(ArticlesModelTest)
            advanceUntilIdle()
            val items = repository.getTopHeadLinesByCategoryNews(category = "sports").asSnapshot { }
            val newsModel = items.first()
            assertEquals(40, items.size)
            assertEquals(NewsTest.title, newsModel.title)
            assertEquals(NewsTest.description, newsModel.description)
            assertEquals(NewsTest.url, newsModel.url)
            assertEquals(NewsTest.imageUrl, newsModel.imageUrl)
            assertEquals(NewsTest.author, newsModel.author)
            assertEquals(NewsTest.source, newsModel.source)
            assertEquals(NewsTest.publishedAt.toString(), newsModel.publishedAt.toString())
            assertEquals(NewsTest.content, newsModel.content)
        }


    @Test
    fun `given a news in cache when repo getNews then returns an either right`() = runTest {
        every { cacheDataSource.getNews(url = NewsTest.url) } returns NewsTest
        val either = repository.getNews(url = NewsTest.url)
        assertTrue(either.isRight)
        assertEquals(NewsTest, either.data)
    }

    @Test
    fun `given no news in cache when repo getNews then returns an either left`() = runTest {
        every { cacheDataSource.getNews(url = NewsTest.url) } returns null
        val either = repository.getNews(url = NewsTest.url)
        assertTrue(either.isLeft)
        assertEquals("Item not in cache", either.error.message)
    }

    @Test
    fun `given a watchlist in newsDao when repo getWatchList then returns a flow of News`() =
        runTest {
            every { cacheDataSource.addNews(any()) } just Runs
            every { newsDao.getAll() } returns flow { emit(listOf(NewsEntityTest)) }
            val news = repository.getWatchList()
            assertEquals(NewsTest.title, news.first()[0].title)
            assertEquals(NewsTest.description, news.first()[0].description)
            assertEquals(NewsTest.url, news.first()[0].url)
            assertEquals(NewsTest.imageUrl, news.first()[0].imageUrl)
            assertEquals(NewsTest.author, news.first()[0].author)
            assertEquals(NewsTest.source, news.first()[0].source)
            assertEquals(NewsTest.publishedAt.toString(), news.first()[0].publishedAt.toString())
            assertEquals(NewsTest.content, news.first()[0].content)
        }

}