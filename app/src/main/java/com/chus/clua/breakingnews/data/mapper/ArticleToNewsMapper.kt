package com.chus.clua.breakingnews.data.mapper

import com.chus.clua.breakingnews.data.model.ArticleModel
import com.chus.clua.breakingnews.domain.model.News
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleToNewsMapper @Inject constructor() : AbstractRemoteMapper<ArticleModel, News>() {

    override fun mapFromRemote(remoteModel: ArticleModel) =
        with(remoteModel) {
            News(
                title,
                description,
                urlToImage,
                author,
                source?.name,
                publishedAt,
                content
            )
        }
}