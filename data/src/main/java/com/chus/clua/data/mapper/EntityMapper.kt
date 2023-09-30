package com.chus.clua.data.mapper

import com.chus.clua.data.db.entities.NewsEntity
import com.chus.clua.domain.model.News
import java.text.SimpleDateFormat
import java.util.Date


fun NewsEntity.toNews() =
    News(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        author = author,
        source = source,
        publishedAt =  publishedAt?.toDate(),
        content = content
    )

fun News.toEntity() =
    NewsEntity(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        author = author,
        source = source,
        publishedAt = publishedAt?.toFormatString(),
        content = content
    )

private fun Date?.toFormatString(): String? {
    return try {
        SimpleDateFormat(ENTITY_DATE_FORMAT).format(this)
    } catch (e: Exception) {
        ""
    }
}

private fun String?.toDate(): Date? {
    return try {
        SimpleDateFormat(ENTITY_DATE_FORMAT).parse(this)
    } catch (e: Exception) {
        null
    }
}

private const val ENTITY_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"