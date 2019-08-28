package com.example.quicknews

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter
    private var list: List<News> = ArrayList<News>()

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
        list = newsDatabase.getNewsDao().getAllNews()
        newsAdapter = NewsAdapter(list, arguments!!.getInt("COLOR"))
        rvNewsFavourite.layoutManager = LinearLayoutManager(requireContext())
        rvNewsFavourite.adapter = newsAdapter
    }
}
