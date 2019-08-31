package com.example.quicknews.database

import android.arch.persistence.room.*
import android.provider.ContactsContract
import com.example.quicknews.model.News

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(news: News): Long

    @Query("SELECT * FROM news WHERE type = :typeOfNews")
    fun getAllNewsOfType(typeOfNews: String): List<News>

    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>

    @Delete
    fun deleteNews(news: News)
}