package com.example.quicknews.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

class NewsResponse(var articles: ArrayList<News>)

@Entity
class News(
    @PrimaryKey var title: String,
    @Embedded var source: Source?,
    var author: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?,
    var type: String?
)

class Source(
    var id: String?,
    var name: String?
)
