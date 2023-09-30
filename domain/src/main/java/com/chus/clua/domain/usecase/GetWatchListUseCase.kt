package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.model.News
import com.chus.clua.domain.repository.NewsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


class GetWatchListUseCase @Inject constructor(
    private val repository: NewsRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    operator fun invoke(): Flow<List<News>> = repository.getWatchList().flowOn(dispatcherIO)
}