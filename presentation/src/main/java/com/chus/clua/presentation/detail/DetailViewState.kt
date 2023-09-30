package com.chus.clua.presentation.detail

import com.chus.clua.presentation.models.NewsUI

data class DetailViewState(
    val news: NewsUI? = null,
    val error: Boolean = false
)