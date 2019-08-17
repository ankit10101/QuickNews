package com.example.quicknews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(private val newsItems: List<News>, private val color:Int) : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): NewsHolder {
        val inflatedView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_news, viewGroup, false)
        return NewsHolder(inflatedView)
    }

    override fun getItemCount() = newsItems.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) {
        val currentNews = newsItems[position]
        Log.e("TAG", currentNews.title.toString())
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
        newsHolder.itemView.setOnClickListener {
            val i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse(currentNews.url)
            newsHolder.itemView.context.startActivity(i)
        }
    }

    class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}