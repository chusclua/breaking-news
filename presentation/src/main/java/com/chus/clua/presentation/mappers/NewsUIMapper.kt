package com.chus.clua.presentation.mappers

import com.chus.clua.domain.model.News
import com.chus.clua.presentation.models.NewsUI
import java.text.SimpleDateFormat
import java.util.Date

fun News.toNewsUI() =
    NewsUI(
        title = title,
        description = description,
        imageUrl = imageUrl,
        url = url,
        source = source,
        publishedAt = publishedAt.toString()
    )

private fun Date?.toString(): String {
    return try {
        SimpleDateFormat(DATE_FORMAT).format(this)
    } catch (e: Exception) {
        ""
    }
}

private const val DATE_FORMAT = "MM-dd-yyyy"