package com.chus.clua.breakingnews.domain.mapper

import com.chus.clua.breakingnews.utils.news
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NewsToNewListMapperTest {

    private val mapper = NewsToNewListMapper()

    @Test
    fun `WHEN mapFromDomain THEN obtains a UiModel`() {
        with(mapper.mapFromDomain(news)) {
            assertEquals("imageUrl", imageUrl)
            assertEquals("title", title)
            assertEquals("description", subtitle)
            assertEquals(news, item)
        }
    }

    @Test
    fun `WHEN mapFromDomainList THEN obtains a list of UiModel`() {
        with(mapper.mapFromDomainList(listOf(news))) {
            assertTrue(isNotEmpty())
            assertEquals("imageUrl", get(0).imageUrl)
            assertEquals("title", get(0).title)
            assertEquals("description", get(0).subtitle)
            assertEquals(news, get(0).item)
        }
    }

}