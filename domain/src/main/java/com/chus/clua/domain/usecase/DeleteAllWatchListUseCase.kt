package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.repository.NewsRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteAllWatchListUseCase @Inject constructor(
    private val repository: NewsRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke() =
        withContext(dispatcherIO) {
            repository.deleteAll()
        }
}