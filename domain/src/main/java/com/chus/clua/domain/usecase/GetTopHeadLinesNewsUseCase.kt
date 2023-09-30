package com.chus.clua.domain.usecase

import androidx.paging.PagingData
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.model.News
import com.chus.clua.domain.repository.NewsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetTopHeadLinesNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    operator fun invoke(category: String): Flow<PagingData<News>> =
        repository.getTopHeadLinesByCategoryNews(category).flowOn(dispatcherIO)
}