package com.chus.clua.breakingnews.data.network

import com.chus.clua.breakingnews.data.utils.serviceHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsClient @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getTopHeadlines(countryCode: String?, categoryKey: String?) =
        serviceHandler {
            newsApi.getTopHeadlines(countryCode, categoryKey)
        }
}