package com.chus.clua.breakingnews.data.network

import com.chus.clua.breakingnews.data.model.ArticlesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val GET_LATEST_NEWS_PATH = "/v2/top-headlines"

private const val COUNTRY_PARAM = "country"
private const val CATEGORY_PARAM = "category"

interface NewsApi {

    @GET(GET_LATEST_NEWS_PATH)
    suspend fun getTopHeadlines(
        @Query(COUNTRY_PARAM) countryCode: String?,
        @Query(CATEGORY_PARAM) category: String?
    ): Response<ArticlesModel>
}