package com.example.quicknews

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

class NewsResponse(var articles: ArrayList<News>)

@Entity
class News(
    @PrimaryKey(autoGenerate = true)
    val newsId: Int,
    @Embedded var source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?,
    var type: String?,
    var checked: Boolean
)

class Source(
    var id: String?,
    var name: String?
)
