package com.chus.clua.breakingnews.utils

import com.chus.clua.breakingnews.data.model.ArticleModel
import com.chus.clua.breakingnews.data.model.ArticlesModel
import com.chus.clua.breakingnews.data.model.SourceModel

val sourceModel = SourceModel(
    "source id",
    "source name"
)

val articleModel = ArticleModel(
    sourceModel,
    "author",
    "title",
    "description",
    "url",
    "urlToImage",
    null,
    "content"
)

val articlesModel = ArticlesModel(
    "ok",
    1,
    null
)