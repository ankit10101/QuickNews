package com.example.quicknews.ui.adapter

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quicknews.R
import com.example.quicknews.database.NewsDatabase
import com.example.quicknews.model.News
import com.example.quicknews.ui.activity.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private var newsItems: List<News>, private val color: Int) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private lateinit var context: Context

    private val newsDatabase by lazy {
        Room.databaseBuilder(context, NewsDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): NewsHolder {
        val inflatedView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_news, viewGroup, false)
        context = viewGroup.context
        return NewsHolder(inflatedView)
    }

    override fun getItemCount() = newsItems.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) {
        val currentNews = newsItems[position]
        with(newsHolder.itemView) {
            card_view.setCardBackgroundColor(color)
            tvSource.text = currentNews.source!!.name.toString()
            tvTitle.text = currentNews.title.toString()
            tvPublishedAt.text = "Published at: " + currentNews.publishedAt.toString()
            tvDesc.text = currentNews.description.toString()
            Picasso.get()
                .load(currentNews.urlToImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_failed)
                .into(ivImage)
        }
        newsHolder.itemView.textViewOptions.setOnClickListener {
            val popup = PopupMenu(context, newsHolder.itemView.textViewOptions)
            //inflating menu from xml resource
            popup.inflate(com.example.quicknews.R.menu.options_menu)
            //adding click listener
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu1 -> {
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(context, Uri.parse(currentNews.url))
                    }
                    R.id.menu2 -> {
                        newsDatabase.getNewsDao().insertNews(currentNews)
                        Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                false
            }
            //displaying the popup
            popup.show()
        }
    }

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}