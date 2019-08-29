package com.example.quicknews.ui.fragment

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quicknews.model.News
import com.example.quicknews.database.NewsDatabase
import com.example.quicknews.ui.adapter.NewsFavouriteAdapter
import com.example.quicknews.R
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : Fragment() {

    private lateinit var newsFavouriteAdapter: NewsFavouriteAdapter
    private var list: ArrayList<News> = ArrayList()

    private val newsDatabase by lazy {
        Room.databaseBuilder(requireContext(), NewsDatabase::class.java, "news.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        //For getting a new instance of the fragment
        fun newInstance(color: Int): FavouriteFragment {
            return FavouriteFragment().apply {
                val bundle = Bundle()
                bundle.putInt("COLOR", color)
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = newsDatabase.getNewsDao().getAllNews() as ArrayList<News>
        if(list.isNotEmpty()){
            tvNoNewsAddedtoFav.visibility = View.INVISIBLE
            rvNewsFavourite.visibility = View.VISIBLE
        }
        newsFavouriteAdapter =
            NewsFavouriteAdapter(list, arguments!!.getInt("COLOR"))
        rvNewsFavourite.layoutManager = LinearLayoutManager(requireContext())
        rvNewsFavourite.adapter = newsFavouriteAdapter
    }
}
