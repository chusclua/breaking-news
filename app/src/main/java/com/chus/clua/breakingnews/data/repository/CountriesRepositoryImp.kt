package com.chus.clua.breakingnews.data.repository

import com.chus.clua.breakingnews.data.local.GsonFileReader
import com.chus.clua.breakingnews.domain.model.Country
import com.chus.clua.breakingnews.domain.repository.CountriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val COUNTRIES_JSON = "countries.json"

class CountriesRepositoryImp(
    private val gsonReader: GsonFileReader,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : CountriesRepository {
    override suspend fun getAvailableCountries(): List<Country> {
        return try {
            withContext(dispatcherIO) { gsonReader.parse(COUNTRIES_JSON) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}