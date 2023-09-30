package com.chus.clua.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chus.clua.data.db.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsEntity)

    @Query("DELETE FROM news WHERE url = :url")
    fun delete(url: String)

    @Query("DELETE FROM news")
    fun deleteAll()

    @Query("SELECT count(*) from news WHERE url LIKE :url")
    fun contains(url: String): Boolean

}