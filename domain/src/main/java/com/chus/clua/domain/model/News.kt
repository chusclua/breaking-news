package com.chus.clua.domain.model

import java.util.*

data class News(
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val author: String?,
    val source: String?,
    val publishedAt: Date?,
    val content: String?
)