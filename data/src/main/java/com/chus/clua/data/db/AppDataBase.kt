package com.chus.clua.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chus.clua.data.db.entities.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

}