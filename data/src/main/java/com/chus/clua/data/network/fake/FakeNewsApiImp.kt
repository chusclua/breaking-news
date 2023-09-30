package com.chus.clua.data.network.fake

import com.chus.clua.data.network.model.ArticleModel
import com.chus.clua.data.network.model.ArticlesModel
import com.chus.clua.data.network.model.SourceModel
import com.chus.clua.data.network.NewsApi
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import retrofit2.http.Query

@Singleton
class FakeNewsApiImp @Inject constructor() : BaseFakeApi(), NewsApi {
    override suspend fun getTopHeadLinesNews(
        @Query(value = "pageSize") pageSize: Int,
        @Query(value = "page") page: Int,
        @Query(value = "category") category: String,
        @Query(value = "country") country: String
    ): Response<ArticlesModel> = response()

    private fun response() = when (isFailure) {
        true -> Response.error(500, "".toResponseBody())
        false -> Response.success(200, articlesModel)
    }

    private val sourceModel = SourceModel(id = null, name = "Slashdot.org")

    private val articleModel = ArticleModel(
        source = sourceModel,
        author = "msmash",
        title = "First Bitcoin ETF Could Be Coming Soon as Court Rules in Favor of Grayscale Over SEC",
        description = "The U.S. Court of Appeals for the D.C. Circuit has paved the way for bitcoin exchange-traded funds. From a report: On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's applicatio…",
        url = "https://slashdot.org/story/23/08/29/1816233/first-bitcoin-etf-could-be-coming-soon-as-court-rules-in-favor-of-grayscale-over-sec",
        urlToImage = "https://a.fsdn.com/sd/topics/bitcoin_64.png",
        publishedAt = Calendar.getInstance().apply { set(2000, 9, 10, 0, 0, 0) }.time,
        content = "On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's application to convert the Grayscale Bitcoin Trust to an ETF. The… [+1393 chars]"
    )

    private val articlesModel = ArticlesModel(
        status = "ok",
        totalResults = 1,
        articles = buildPage()
    )

    private fun buildPage(): List<ArticleModel> {
        val list = mutableListOf<ArticleModel>()
        repeat(10) {
            list.add(articleModel)
        }
        repeat(10) {
            list.add(articleModel.copy(url = "url to test"))
        }
        return list
    }

}