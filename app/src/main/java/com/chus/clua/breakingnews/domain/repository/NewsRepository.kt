package com.chus.clua.breakingnews.domain.repository

import com.chus.clua.breakingnews.domain.Either
import com.chus.clua.breakingnews.domain.model.News

interface NewsRepository {
    suspend fun getTopHeadlinesByCountryAndCategory(countryCode: String?, categoryKey: String?): Either<Exception, List<News>>
}