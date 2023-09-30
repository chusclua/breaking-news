package com.chus.clua.presentation.compose

import android.content.Context
import android.content.Intent


fun Context.shareAsPlainText(
    title: String? = null,
    content: String
) {
    Intent.createChooser(
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            title?.let { putExtra(Intent.EXTRA_TITLE, it) }
            type = "text/plain"
        },
        null
    ).let { this.startActivity(it) }
}