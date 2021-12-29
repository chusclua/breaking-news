package com.chus.clua.breakingnews.presentation.features.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chus.clua.breakingnews.presentation.models.NewsList

object NewsComparator : DiffUtil.ItemCallback<NewsList>() {
    override fun areItemsTheSame(oldItem: NewsList, newItem: NewsList) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: NewsList, newItem: NewsList) =
        oldItem == newItem
}