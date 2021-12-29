package com.chus.clua.breakingnews.presentation.binding

import androidx.appcompat.widget.AppCompatImageView

interface ItemClickHandler<T> {
    fun onItemClicked(item: T, imageView: AppCompatImageView)
}