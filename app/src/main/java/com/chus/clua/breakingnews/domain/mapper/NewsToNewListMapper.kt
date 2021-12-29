package com.chus.clua.breakingnews.domain.mapper

import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.presentation.models.NewsList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsToNewListMapper @Inject constructor() : AbstractDomainMapper<News, NewsList>() {

    override fun mapFromDomain(domainModel: News) =
        with(domainModel) {
            NewsList(
                imageUrl,
                title,
                description,
                domainModel
            )
        }
}