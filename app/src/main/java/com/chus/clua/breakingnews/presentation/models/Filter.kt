package com.chus.clua.breakingnews.presentation.models

import android.os.Parcelable
import com.chus.clua.breakingnews.domain.model.Category
import com.chus.clua.breakingnews.domain.model.Country
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val country: Country?,
    val category: Category?
): Parcelable
