package com.example.quicknews

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_favourite.view.*
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

            myToggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    myToggleButton.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.star_yellow
                        )
                    )
                    Log.e("TAG", currentNews.title.toString())
                    newsDatabase.getNewsDao().insertNews(currentNews)
                    notifyDataSetChanged()
                } else {
                    myToggleButton.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.star_grey
                        )
                    )
                    Log.e("TAG", currentNews.title.toString())
                    newsDatabase.getNewsDao().deleteNews(currentNews)
                    notifyDataSetChanged()
                }
            }
        }
        newsHolder.itemView.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse(currentNews.url)
            newsHolder.itemView.context.startActivity(i)
        }
    }

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}