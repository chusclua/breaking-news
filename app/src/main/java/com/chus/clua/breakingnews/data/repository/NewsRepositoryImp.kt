package com.chus.clua.breakingnews.data.repository

import com.chus.clua.breakingnews.data.mapper.ArticleToNewsMapper
import com.chus.clua.breakingnews.data.network.NewsClient
import com.chus.clua.breakingnews.domain.Either
import com.chus.clua.breakingnews.domain.flatMap
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImp (
    private val client: NewsClient,
    private val mapper: ArticleToNewsMapper,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {

    override suspend fun getTopHeadlinesByCountryAndCategory(
        countryCode: String?,
        categoryKey: String?
    ): Either<Exception, List<News>> {
        return withContext(dispatcherIO) {
            client.getTopHeadlines(countryCode, categoryKey)
                .flatMap { articles ->
                    articles.articles?.let { list ->
                        Either.Right(mapper.mapFromRemoteList(list))
                    } ?: run {
                        Either.Left(Exception("Empty body"))
                    }
                }
        }
    }
}