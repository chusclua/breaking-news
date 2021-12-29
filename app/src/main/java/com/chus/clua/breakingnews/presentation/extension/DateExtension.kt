package com.chus.clua.breakingnews.presentation.extension

import java.text.SimpleDateFormat
import java.util.*

const val PATTERN = "dd-MM-yyyy HH:mm"

fun Date?.format(pattern: String = PATTERN): String {
    this?.let { date ->
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
        return simpleDateFormat.format(date)
    } ?: run { return "" }
}