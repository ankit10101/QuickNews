package com.example.quicknews.ui.fragment

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.quicknews.model.News
import com.example.quicknews.database.NewsDatabase
import com.example.quicknews.ui.adapter.NewsFavouriteAdapter
import com.example.quicknews.R
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavouriteFragment : Fragment(), AdapterView.OnItemSelectedListener {

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

        // Spinner click listener
        spinner.onItemSelectedListener = this

        // Spinner Drop down elements
        val categories = java.util.ArrayList<String>()
        categories.add(context!!.getString(R.string.general))
        categories.add(context!!.getString(R.string.business))
        categories.add(context!!.getString(R.string.entertainment))
        categories.add(context!!.getString(R.string.health))
        categories.add(context!!.getString(R.string.science))
        categories.add(context!!.getString(R.string.sports))
        categories.add(context!!.getString(R.string.technology))
        // Creating adapter for spinner
        val dataAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories)
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // attaching data adapter to spinner
        spinner.adapter = dataAdapter
        newsFavouriteAdapter = NewsFavouriteAdapter(list, arguments!!.getInt("COLOR"))
        rvNewsFavourite.layoutManager = LinearLayoutManager(requireContext())
        rvNewsFavourite.adapter = newsFavouriteAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val category = parent?.getItemAtPosition(position).toString()
        list.clear()
        if (category == "General") {
            list.addAll(newsDatabase.getNewsDao().getAllNews())
        } else {
            list.addAll(newsDatabase.getNewsDao().getAllNewsOfType(category))
        }
        newsFavouriteAdapter.notifyDataSetChanged()
    }
}
