package com.chus.clua.breakingnews.domain.repository

import com.chus.clua.breakingnews.domain.model.Category

interface CategoriesRepository {
    suspend fun getAvailableCategories(): List<Category>
}