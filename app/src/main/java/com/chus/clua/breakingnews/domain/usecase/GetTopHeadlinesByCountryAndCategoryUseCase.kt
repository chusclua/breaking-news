package com.chus.clua.breakingnews.domain.usecase

import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.repository.NewsRepository
import javax.inject.Inject

class GetTopHeadlinesByCountryAndCategoryUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(country: Country?, category: Category?) =
        repository.getTopHeadlinesByCountryAndCategory(country?.code, category?.key)
}