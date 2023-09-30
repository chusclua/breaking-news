package com.chus.clua.data.datasource

import com.chus.clua.domain.model.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheDataSource @Inject constructor(
    private val cache: MutableMap<String, News>
) {
    fun addNews(news: News) {
        cache[news.url] = news
    }

    fun getNews(url: String): News? = cache[url]
}