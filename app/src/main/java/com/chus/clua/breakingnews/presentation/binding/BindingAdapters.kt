package com.chus.clua.breakingnews.presentation.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chus.clua.breakingnews.R
import com.chus.clua.breakingnews.presentation.extension.format
import java.util.*


@BindingAdapter("image_url")
fun setImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .error(R.mipmap.ic_launcher_foreground)
            .centerCrop()
            .into(view)
    }
}

@BindingAdapter("created_by", "created_at")
fun setDetailCreated(textView: TextView, createdBy: String?, createdAt: Date?) {
    textView.text = "$createdBy · ${createdAt?.format()}"
}

@BindingAdapter("toast_message")
fun showToast(view: View, message: String?) {
    message?.let { Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show() }
}

@BindingAdapter("has_item_selected")
fun setSelectedBackground(view: View, hasItem: Boolean?) {
    if (hasItem == true) {
        view.background = ContextCompat.getDrawable(view.context, R.drawable.selected_item_background)
    } else {
        view.background = ContextCompat.getDrawable(view.context, R.drawable.unselected_item_background)
    }
}