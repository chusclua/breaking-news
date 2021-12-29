package com.chus.clua.breakingnews.presentation.features.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chus.clua.breakingnews.databinding.ItemNewsBinding
import com.chus.clua.breakingnews.domain.model.News
import com.chus.clua.breakingnews.presentation.binding.ItemClickHandler
import com.chus.clua.breakingnews.presentation.models.NewsList

class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: NewsList, handler: ItemClickHandler<News>) {
        binding.item = item
        binding.handler = handler
        binding.executePendingBindings()
    }
}