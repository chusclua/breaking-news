package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.repository.NewsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class IsOnWatchListUseCase @Inject constructor(
    private val repository: NewsRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(url: String): Boolean =
        withContext(dispatcherIO) {
            repository.isOnWatchList(url)
        }
}