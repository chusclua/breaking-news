package com.chus.clua.breakingnews.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class News(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val author: String?,
    val source: String?,
    val publishedAt: Date?,
    val content: String?
): Parcelable