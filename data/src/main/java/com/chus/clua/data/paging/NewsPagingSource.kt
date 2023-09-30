package com.chus.clua.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chus.clua.data.network.model.ArticleModel
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.serviceHandler
import com.chus.clua.domain.fold
import com.chus.clua.domain.onRight

class NewsPagingSource(
    private val dataSource: RemoteDataSource,
    private val category: String
): PagingSource<Int, ArticleModel>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleModel> {
        return try {
            val page = params.key ?: 1

            val articles: MutableList<ArticleModel> = mutableListOf()
            dataSource.getTopHeadLinesNews(page = page, category = category).onRight { data ->
                data.articles?.let { articles.addAll(it) }
            }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (articles.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}