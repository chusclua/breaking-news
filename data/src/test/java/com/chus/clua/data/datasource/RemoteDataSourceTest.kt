package com.chus.clua.data.datasource


import com.chus.clua.data.network.NewsApi
import com.chus.clua.data.utils.ArticlesModelTest
import com.chus.clua.domain.data
import com.chus.clua.domain.error
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var api: NewsApi

    private lateinit var dataSource: RemoteDataSource

    @Before
    fun setUp() {
        dataSource = RemoteDataSource(api)
    }

    @Test
    fun `given a success response when service getEverythingNews then obtains a either right`() = runTest {
        coEvery { api.getTopHeadLinesNews(page = 1, category = "sports") } returns Response.success(ArticlesModelTest)
        val either = dataSource.getTopHeadLinesNews(page = 1, category = "sports")
        assertTrue(either.isRight)
        assertEquals(ArticlesModelTest, either.data)
    }

    @Test
    fun `given a failure response when service getEverythingNews then obtains a either left`() = runTest {
        coEvery { api.getTopHeadLinesNews(page = 1, category = "sports") } returns Response.error(500, "{}".toResponseBody())
        val either = dataSource.getTopHeadLinesNews(page = 1, category = "sports")
        assertTrue(either.isLeft)
        assertEquals("Response.error()", either.error.message)
    }
}