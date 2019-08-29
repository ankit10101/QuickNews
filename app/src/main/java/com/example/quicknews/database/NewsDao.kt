package com.example.quicknews.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.quicknews.model.News

@Dao
interface NewsDao {
    @Insert
    fun insertNews(news: News)

    @Query("SELECT * FROM news WHERE type = :typeOfNews")
    fun getAllNewsOfType(typeOfNews: String): List<News>

    @Query("SELECT * FROM news")
    fun getAllNews(): List<News>

    @Query("SELECT * FROM news WHERE newsId = :id")
    fun getNews(id: Int) : News

    @Delete
    fun deleteNews(news: News)
}