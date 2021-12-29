package com.chus.clua.breakingnews.data.repository

import com.chus.clua.breakingnews.data.local.GsonFileReader
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.repository.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val CATEGORIES_JSON = "categories.json"

class CategoriesRepositoryImp(
    private val gsonReader: GsonFileReader,
    private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) : CategoriesRepository {
    override suspend fun getAvailableCategories(): List<Category> {
        return try {
            withContext(dispatcherIO) { gsonReader.parse(CATEGORIES_JSON) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}