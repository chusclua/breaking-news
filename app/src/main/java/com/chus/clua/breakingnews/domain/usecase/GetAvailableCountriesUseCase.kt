package com.chus.clua.breakingnews.domain.usecase

import com.chus.clua.breakingnews.domain.repository.CountriesRepository
import javax.inject.Inject

class GetAvailableCountriesUseCase @Inject constructor(private val repository: CountriesRepository) {
    suspend operator fun invoke() = repository.getAvailableCountries().sortedBy { it.name }
}