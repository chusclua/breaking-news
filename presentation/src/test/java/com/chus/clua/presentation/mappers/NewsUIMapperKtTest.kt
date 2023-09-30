package com.chus.clua.presentation.mappers

import com.chus.clua.presentation.utils.NewsTest
import com.chus.clua.presentation.utils.NewsUITest
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsUIMapperKtTest {
    @Test
    fun `ArticleModel to News mapper test`() {
        val newsUIModel = NewsTest.toNewsUI()
        assertEquals(NewsUITest.title, newsUIModel.title)
        assertEquals(NewsUITest.description, newsUIModel.description)
        assertEquals(NewsUITest.url, newsUIModel.url)
        assertEquals(NewsUITest.imageUrl, newsUIModel.imageUrl)
        assertEquals(NewsUITest.source, newsUIModel.source)
        assertEquals(NewsUITest.publishedAt, newsUIModel.publishedAt)
        assertEquals(NewsUITest.isOnWatchList, newsUIModel.isOnWatchList)
    }

}