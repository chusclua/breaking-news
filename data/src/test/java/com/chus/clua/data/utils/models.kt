package com.chus.clua.data.utils

import com.chus.clua.data.db.entities.NewsEntity
import com.chus.clua.data.network.model.ArticleModel
import com.chus.clua.data.network.model.ArticlesModel
import com.chus.clua.data.network.model.SourceModel
import com.chus.clua.domain.model.News
import java.util.Calendar

val NewsTest = News(
    author = "msmash",
    title = "First Bitcoin ETF Could Be Coming Soon as Court Rules in Favor of Grayscale Over SEC",
    description = "The U.S. Court of Appeals for the D.C. Circuit has paved the way for bitcoin exchange-traded funds. From a report: On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's applicatio…",
    url = "https://slashdot.org/story/23/08/29/1816233/first-bitcoin-etf-could-be-coming-soon-as-court-rules-in-favor-of-grayscale-over-sec",
    imageUrl = "https://a.fsdn.com/sd/topics/bitcoin_64.png",
    publishedAt = Calendar.getInstance().apply { set(2000, 9, 10, 0, 0, 0) }.time,
    content = "On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's application to convert the Grayscale Bitcoin Trust to an ETF. The… [+1393 chars]",
    source = "Slashdot.org"
)

val NewsEntityTest = NewsEntity(
    author = "msmash",
    title = "First Bitcoin ETF Could Be Coming Soon as Court Rules in Favor of Grayscale Over SEC",
    description = "The U.S. Court of Appeals for the D.C. Circuit has paved the way for bitcoin exchange-traded funds. From a report: On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's applicatio…",
    url = "https://slashdot.org/story/23/08/29/1816233/first-bitcoin-etf-could-be-coming-soon-as-court-rules-in-favor-of-grayscale-over-sec",
    imageUrl = "https://a.fsdn.com/sd/topics/bitcoin_64.png",
    publishedAt = "2000-10-10T00:00:00Z",
    content = "On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's application to convert the Grayscale Bitcoin Trust to an ETF. The… [+1393 chars]",
    source = "Slashdot.org"
)

private val SourceModelTest = SourceModel(id = null, name = "Slashdot.org")

val ArticleModelTest = ArticleModel(
    source = SourceModelTest,
    author = "msmash",
    title = "First Bitcoin ETF Could Be Coming Soon as Court Rules in Favor of Grayscale Over SEC",
    description = "The U.S. Court of Appeals for the D.C. Circuit has paved the way for bitcoin exchange-traded funds. From a report: On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's applicatio…",
    url = "https://slashdot.org/story/23/08/29/1816233/first-bitcoin-etf-could-be-coming-soon-as-court-rules-in-favor-of-grayscale-over-sec",
    urlToImage = "https://a.fsdn.com/sd/topics/bitcoin_64.png",
    publishedAt = Calendar.getInstance().apply { set(2000, 9, 10, 0, 0, 0) }.time,
    content = "On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's application to convert the Grayscale Bitcoin Trust to an ETF. The… [+1393 chars]"
)

val ArticlesModelTest = ArticlesModel(
    status = "ok",
    totalResults = 1,
    articles = buildPage()
)

private fun buildPage(): List<ArticleModel> {
    val list = mutableListOf<ArticleModel>()
    repeat(20) {
        list.add(ArticleModelTest)
    }
    return list
}