package com.chus.clua.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val author: String?,
    val source: String?,
    val publishedAt: String?,
    val content: String?
)
