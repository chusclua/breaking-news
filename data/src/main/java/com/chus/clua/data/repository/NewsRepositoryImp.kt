package com.chus.clua.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.filter
import androidx.paging.map
import com.chus.clua.data.datasource.CacheDataSource
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.db.NewsDao
import com.chus.clua.data.mapper.toEntity
import com.chus.clua.data.mapper.toNews
import com.chus.clua.data.paging.NewsPagingSource
import com.chus.clua.domain.Either
import com.chus.clua.domain.model.News
import com.chus.clua.domain.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class NewsRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource,
    private val newsDao: NewsDao
) : NewsRepository {
    override fun getTopHeadLinesByCategoryNews(category: String) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            NewsPagingSource(remoteDataSource, category)
        }
    ).flow.map { pagingData ->
        pagingData.map { article ->
            article.toNews().also { news ->
                cacheDataSource.addNews(news)
            }
        }.filter { news ->
            !news.title.contains("removed", ignoreCase = true)
        }
    }

    override fun getNews(url: String): Either<Exception, News> {
        return cacheDataSource.getNews(url)?.let { news ->
            Either.Right(news)
        } ?: run {
            Either.Left(Exception("Item not in cache"))
        }
    }

    override fun saveToWatchList(url: String) {
        cacheDataSource.getNews(url)?.let {
            newsDao.insert(it.toEntity())
        }
    }

    override fun deleteOnWatchList(url: String) {
        newsDao.delete(url)
    }

    override fun deleteAll() {
        newsDao.deleteAll()
    }

    override fun getWatchList(): Flow<List<News>> {
        return newsDao.getAll().map { list ->
            list.map {
                it.toNews().also { news ->
                    cacheDataSource.addNews(news)
                }
            }
        }
    }

    override fun isOnWatchList(url: String): Boolean {
        return newsDao.contains(url)
    }
}
