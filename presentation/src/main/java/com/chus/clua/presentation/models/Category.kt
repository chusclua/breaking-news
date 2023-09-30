package com.chus.clua.presentation.models

import androidx.annotation.StringRes
import com.chus.clua.presentation.R

enum class Category(
    val key: String,
    val position: Int,
    @StringRes
    val nameResource: Int
) {
    BUSINESS(key = "business", position = 0, nameResource = R.string.busines),
    ENTERTAINMENT(key = "entertainment", position = 1, nameResource = R.string.entertainment),
    GENERAL(key = "general", position = 2, nameResource = R.string.general),
    HEALTH(key = "health", position = 3, nameResource = R.string.health),
    SCIENCE(key = "science", position = 4, nameResource = R.string.science),
    SPORTS(key = "sports", position = 5, nameResource = R.string.sports),
    TECHNOLOGY(key = "technology", position = 6, nameResource = R.string.technology)
}