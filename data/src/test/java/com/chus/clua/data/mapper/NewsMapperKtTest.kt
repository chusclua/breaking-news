package com.chus.clua.data.mapper


import com.chus.clua.data.utils.ArticleModelTest
import com.chus.clua.data.utils.NewsEntityTest
import com.chus.clua.data.utils.NewsTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NewsMapperKtTest {

    @Test
    fun `ArticleModel to News mapper test`() {
        val newsModel = ArticleModelTest.toNews()
        assertEquals(NewsTest.title, newsModel.title)
        assertEquals(NewsTest.description, newsModel.description)
        assertEquals(NewsTest.url, newsModel.url)
        assertEquals(NewsTest.imageUrl, newsModel.imageUrl)
        assertEquals(NewsTest.author, newsModel.author)
        assertEquals(NewsTest.source, newsModel.source)
        assertEquals(NewsTest.publishedAt.toString(), newsModel.publishedAt.toString())
        assertEquals(NewsTest.content, newsModel.content)
    }
}