package com.example.quicknews.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.quicknews.model.News

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}