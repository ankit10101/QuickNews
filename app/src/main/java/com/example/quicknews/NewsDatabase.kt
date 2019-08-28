package com.example.quicknews

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}