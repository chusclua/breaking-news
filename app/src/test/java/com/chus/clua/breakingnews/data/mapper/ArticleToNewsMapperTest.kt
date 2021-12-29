package com.chus.clua.breakingnews.data.mapper

import com.chus.clua.breakingnews.utils.articleModel
import org.junit.Assert.*
import org.junit.Test

class ArticleToNewsMapperTest {

    private val mapper = ArticleToNewsMapper()

    @Test
    fun `WHEN mapFromRemote THEN obtains a DomainModel`() {
        with(mapper.mapFromRemote(articleModel)) {
            assertEquals("title", title)
            assertEquals("description", description)
            assertEquals("urlToImage", imageUrl)
            assertEquals("author", author)
            assertEquals("source name", source)
            assertNull(publishedAt)
            assertEquals("content", content)
        }
    }

    @Test
    fun `WHEN mapFromRemoteList THEN obtains a list of DomainModel`() {
        with(mapper.mapFromRemoteList(listOf(articleModel))) {
            assertTrue(isNotEmpty())
            assertEquals("title", get(0).title)
            assertEquals("description", get(0).description)
            assertEquals("urlToImage", get(0).imageUrl)
            assertEquals("author", get(0).author)
            assertEquals("source name", get(0).source)
            assertNull(get(0).publishedAt)
            assertEquals("content", get(0).content)
        }
    }
}