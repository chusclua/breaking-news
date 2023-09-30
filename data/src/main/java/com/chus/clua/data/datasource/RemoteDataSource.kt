package com.chus.clua.data.datasource

import com.chus.clua.data.network.model.ArticlesModel
import com.chus.clua.data.network.NewsApi
import com.chus.clua.data.serviceHandler
import com.chus.clua.domain.Either
import com.chus.clua.domain.onRight
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Response

@Singleton
class RemoteDataSource @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getTopHeadLinesNews(page: Int, category: String): Either<Exception, ArticlesModel> =
        serviceHandler { newsApi.getTopHeadLinesNews(page = page, category = category) }


}