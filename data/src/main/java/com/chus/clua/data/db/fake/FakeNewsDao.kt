package com.chus.clua.data.db.fake

import com.chus.clua.data.db.NewsDao
import com.chus.clua.data.db.entities.NewsEntity
import com.chus.clua.data.mapper.toEntity
import com.chus.clua.domain.model.News
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class FakeNewsDao @Inject constructor() : NewsDao {

    private val NewsTest = News(
        author = "msmash",
        title = "First Bitcoin ETF Could Be Coming Soon as Court Rules in Favor of Grayscale Over SEC",
        description = "The U.S. Court of Appeals for the D.C. Circuit has paved the way for bitcoin exchange-traded funds. From a report: On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's applicatio…",
        url = "https://slashdot.org/story/23/08/29/1816233/first-bitcoin-etf-could-be-coming-soon-as-court-rules-in-favor-of-grayscale-over-sec",
        imageUrl = "https://a.fsdn.com/sd/topics/bitcoin_64.png",
        publishedAt = Calendar.getInstance().apply { set(2000, 9, 10, 0, 0, 0) }.time,
        content = "On Tuesday, the court sided with Grayscale in a lawsuit against the Securities and Exchange Commission which had denied the company's application to convert the Grayscale Bitcoin Trust to an ETF. The… [+1393 chars]",
        source = "Slashdot.org"
    )

    private val watchList = MutableStateFlow(listOf(NewsTest.toEntity()))

    override fun getAll(): Flow<List<NewsEntity>> {
        return watchList
    }

    override fun insert(news: NewsEntity) {
        watchList.update { it + news }
    }

    override fun delete(url: String) {
        val entity = watchList.value.find { it.url == url }
        entity?.let { news ->
            watchList.update { it - news }
        }
    }

    override fun deleteAll() {
        watchList.update { emptyList() }
    }

    override fun contains(url: String): Boolean {
        return watchList.value.find { it.url == url } != null
    }
}