package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.ArticleModel
import com.chus.clua.domain.model.News

fun ArticleModel.toNews() =
    News(
        title = title,
        description = description,
        url = url,
        imageUrl = urlToImage,
        author = author,
        source = source?.name,
        publishedAt =  publishedAt,
        content = content
    )