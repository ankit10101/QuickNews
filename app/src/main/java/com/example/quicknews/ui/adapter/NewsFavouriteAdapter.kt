package com.example.quicknews.ui.adapter

import android.annotation.SuppressLint
import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quicknews.model.News
import com.example.quicknews.database.NewsDatabase
import com.example.quicknews.R
import kotlinx.android.synthetic.main.item_news.view.*

class NewsFavouriteAdapter(private var newsItems: ArrayList<News>, private val color: Int) :
    RecyclerView.Adapter<NewsFavouriteAdapter.NewsFavouriteHolder>() {

    private lateinit var context: Context

    private val newsDatabase by lazy {
        Room.databaseBuilder(context, NewsDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): NewsFavouriteHolder {
        val inflatedView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_news, viewGroup, false)
        context = viewGroup.context
        return NewsFavouriteHolder(
            inflatedView
        )
    }

    override fun getItemCount() = newsItems.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(newsFavouriteHolder: NewsFavouriteHolder, position: Int) {
        val currentNews = newsItems[position]
        with(newsFavouriteHolder.itemView) {
            card_view.setCardBackgroundColor(color)
            tvSource.text = currentNews.source!!.name.toString()
            tvTitle.text = currentNews.title.toString()
            tvPublishedAt.text = "Published at: " + currentNews.publishedAt.toString()
            tvDesc.text = currentNews.description.toString()
            com.squareup.picasso.Picasso.get()
                .load(currentNews.urlToImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading_failed)
                .into(ivImage)
        }
        newsFavouriteHolder.itemView.textViewOptions.setOnClickListener {
            val popup = PopupMenu(context, newsFavouriteHolder.itemView.textViewOptions)
            //inflating menu from xml resource
            popup.inflate(R.menu.options_menu_favourite)

            //adding click listener
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu1 -> {
                        val i = Intent()
                        i.action = Intent.ACTION_VIEW
                        i.data = Uri.parse(currentNews.url)
                        newsFavouriteHolder.itemView.context.startActivity(i)
                    }
                    R.id.menu2 -> {
                        newsDatabase.getNewsDao().deleteNews(currentNews)
                        newsItems.remove(currentNews)
                        notifyDataSetChanged()
                    }
                }
                false
            }
            //displaying the popup
            popup.show()
        }
    }

    class NewsFavouriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}