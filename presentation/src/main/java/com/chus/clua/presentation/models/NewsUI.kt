package com.chus.clua.presentation.models

data class NewsUI(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val url: String,
    val source: String?,
    val publishedAt: String,
    val isOnWatchList: Boolean = false
)
