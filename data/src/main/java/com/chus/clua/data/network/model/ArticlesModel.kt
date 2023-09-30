package com.chus.clua.data.network.model

data class ArticlesModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleModel>?
)