package com.chus.clua.data.mapper

import com.chus.clua.data.utils.NewsEntityTest
import com.chus.clua.data.utils.NewsTest
import org.junit.Assert.assertEquals
import org.junit.Test

class EntityMapperKtTest {
    @Test
    fun `News to NewsEntity mapper test`() {
        val entity = NewsTest.toEntity()
        assertEquals(NewsEntityTest.title, entity.title)
        assertEquals(NewsEntityTest.description, entity.description)
        assertEquals(NewsEntityTest.url, entity.url)
        assertEquals(NewsEntityTest.imageUrl, entity.imageUrl)
        assertEquals(NewsEntityTest.author, entity.author)
        assertEquals(NewsEntityTest.source, entity.source)
        assertEquals(NewsEntityTest.publishedAt.toString(), entity.publishedAt.toString())
        assertEquals(NewsEntityTest.content, entity.content)
    }

    @Test
    fun `NewsEntity to News mapper test`() {
        val news = NewsEntityTest.toNews()
        assertEquals(NewsTest.title, news.title)
        assertEquals(NewsTest.description, news.description)
        assertEquals(NewsTest.url, news.url)
        assertEquals(NewsTest.imageUrl, news.imageUrl)
        assertEquals(NewsTest.author, news.author)
        assertEquals(NewsTest.source, news.source)
        assertEquals(NewsTest.publishedAt.toString(), news.publishedAt.toString())
        assertEquals(NewsTest.content, news.content)
    }
}