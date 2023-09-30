package com.chus.clua.data.network

import com.chus.clua.data.network.model.ArticlesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET(GET_TOP_HEADLINES_PATH)
    suspend fun getTopHeadLinesNews(
        @Query(PAGE_SIZE_PARAM) pageSize: Int = PAGE_SIZE,
        @Query(PAGE_PARAM) page: Int,
        @Query(CATEGORY_PARAM) category: String,
        @Query(COUNTRY_PARAM) country: String = COUNTRY
    ): Response<ArticlesModel>

}

private const val GET_TOP_HEADLINES_PATH = "/v2/top-headlines"

private const val CATEGORY_PARAM = "category"
private const val PAGE_PARAM = "page"
private const val PAGE_SIZE_PARAM = "pageSize"
private const val COUNTRY_PARAM = "country"

private const val PAGE_SIZE = 20
private const val COUNTRY = "us"