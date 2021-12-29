package com.chus.clua.breakingnews.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    override val name: String,
    val key: String
): NamedEntity, Parcelable
