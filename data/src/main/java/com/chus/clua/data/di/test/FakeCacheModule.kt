package com.chus.clua.data.di.test

import com.chus.clua.data.di.CacheModule
import com.chus.clua.domain.model.News
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.util.Calendar


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CacheModule::class]
)
class FakeCacheModule {

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

    @Provides
    fun provideMap(): MutableMap<String, News> =
        mutableMapOf(NewsTest.url to NewsTest, "url to test" to NewsTest)

}