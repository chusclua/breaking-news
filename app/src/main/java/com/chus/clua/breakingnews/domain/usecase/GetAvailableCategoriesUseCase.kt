package com.chus.clua.breakingnews.domain.usecase

import com.chus.clua.breakingnews.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetAvailableCategoriesUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend operator fun invoke() = repository.getAvailableCategories().sortedBy { it.name }
}