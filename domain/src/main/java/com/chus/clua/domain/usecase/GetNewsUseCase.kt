package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.model.News
import com.chus.clua.domain.repository.NewsRepository
import javax.inject.Inject


class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(url: String): Either<Exception, News> = repository.getNews(url)
}