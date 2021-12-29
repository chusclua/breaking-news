package com.chus.clua.breakingnews.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class GsonFileReader @Inject constructor(private val context: Context, val gson: Gson) {

    inline fun <reified T> parse(fileName: String): T {
        val model = object : TypeToken<T>() {}.type
        return gson.fromJson(getJsonDataFromAsset(fileName), model)
    }

    fun getJsonDataFromAsset(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}