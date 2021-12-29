package com.chus.clua.breakingnews.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

private const val DEFAULT_COUNTRY_NAME = "United States"
private const val DEFAULT_COUNTRY_CODE = "US"

@Parcelize
data class Country(override val name: String, val code: String): NamedEntity, Parcelable {
    companion object {
        fun getDefault() = Country(DEFAULT_COUNTRY_NAME, DEFAULT_COUNTRY_CODE)
    }
}
