package com.chus.clua.data.network.model

import java.util.*

data class ArticleModel(
    val source: SourceModel?,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Date?,
    val content: String?
)