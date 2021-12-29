package com.chus.clua.breakingnews.presentation.features.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.chus.clua.breakingnews.databinding.ItemNewsBinding
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.presentation.binding.ItemClickHandler
import com.chus.clua.breakingnews.presentation.models.NewsList

class NewsAdapter(
    private val handler: ItemClickHandler<News>
) : ListAdapter<NewsList, NewsViewHolder>(NewsComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item, handler) }
    }

}