package com.chus.clua.data.datasource

import com.chus.clua.data.utils.NewsTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class CacheDataSourceTest {

    private lateinit var cacheDataSource: CacheDataSource

    @Test
    fun `given a news in cache when getNews then returns a correct News`() = runTest {
        cacheDataSource = CacheDataSource(mutableMapOf(NewsTest.url to NewsTest))
        val news = cacheDataSource.getNews(url = NewsTest.url)
        assertNotNull(news)
        assertEquals(NewsTest, news)
    }

    @Test
    fun `given a news in cache with other url when getNews then returns a null`() = runTest {
        cacheDataSource = CacheDataSource(mutableMapOf(NewsTest.url to NewsTest))
        val news = cacheDataSource.getNews(url = "other url")
        assertNull(news)
    }

    @Test
    fun `given no news in cache when getNews then returns a null`() = runTest {
        cacheDataSource = CacheDataSource(mutableMapOf())
        val news = cacheDataSource.getNews(url = NewsTest.url)
        assertNull(news)
    }
}