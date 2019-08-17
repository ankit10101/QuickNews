package com.example.quicknews

class NewsResponse(var articles: ArrayList<News>)

class News(
    val newsId: Int,
    var source: Source?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
)

class Source(
    var id: String?,
    var name: String?
)