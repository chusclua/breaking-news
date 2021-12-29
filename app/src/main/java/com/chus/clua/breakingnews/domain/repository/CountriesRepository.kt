package com.chus.clua.breakingnews.domain.repository

import com.chus.clua.breakingnews.domain.model.Country

interface CountriesRepository {
    suspend fun getAvailableCountries(): List<Country>
}