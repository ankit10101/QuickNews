package com.example.quicknews

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface NewsDao {
    @Insert
    fun insertNews(news: News)

    @Insert
    fun insertMultipleNews(newsList: List<News>)

    @Query("SELECT * FROM news WHERE type = :typeOfNews")
    fun getAllNewsOfType(typeOfNews: String): List<News>

    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>

    @Query("DELETE FROM news WHERE type = :typeOfNews")
    fun deleteNewsOfType(typeOfNews: String)

    @Delete
    fun deleteNews(news: News)
}