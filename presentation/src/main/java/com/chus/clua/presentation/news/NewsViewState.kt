package com.chus.clua.presentation.news

import com.chus.clua.presentation.models.Category
import com.chus.clua.presentation.models.NewsUI


data class NewsViewState(
    val watchList: List<NewsUI> = emptyList(),
    val categories: List<Category> = Category.values().toList(),
    val bottomBarIndex: Int = 0,
    val selectedCategory: Category = Category.BUSINESS
)
