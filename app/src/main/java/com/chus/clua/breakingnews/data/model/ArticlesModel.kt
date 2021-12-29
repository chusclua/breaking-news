package com.chus.clua.breakingnews.data.model

data class ArticlesModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleModel>?
)