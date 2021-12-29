package com.chus.clua.breakingnews.presentation.models

import com.chus.clua.breakingnews.domain.model.News

data class NewsList(
    val imageUrl: String?,
    val title: String?,
    val subtitle: String?,
    val item: News
)