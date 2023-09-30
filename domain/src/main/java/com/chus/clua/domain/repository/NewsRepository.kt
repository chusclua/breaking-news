package com.chus.clua.domain.repository

import androidx.paging.PagingData
import com.chus.clua.domain.Either
import com.chus.clua.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadLinesByCategoryNews(category: String): Flow<PagingData<News>>

    fun getNews(url: String): Either<Exception, News>

    fun saveToWatchList(url: String)

    fun deleteOnWatchList(url: String)

    fun deleteAll()

    fun getWatchList(): Flow<List<News>>

    fun isOnWatchList(url: String) : Boolean
}