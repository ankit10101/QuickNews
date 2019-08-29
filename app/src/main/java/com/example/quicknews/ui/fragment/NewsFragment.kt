package com.example.quicknews.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quicknews.model.News
import com.example.quicknews.ui.adapter.NewsAdapter
import com.example.quicknews.model.NewsResponse
import com.example.quicknews.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_news.*
import okhttp3.*
import java.io.IOException

class NewsFragment : Fragment() {
    companion object{
        //For getting a new instance of the fragment
        fun newInstance(category: String, color:Int): NewsFragment {
            return NewsFragment().apply {
                val bundle = Bundle()
                bundle.putString("CATEGORY",category)
                bundle.putInt("COLOR",color)
                arguments = bundle
            }
        }
    }
    private lateinit var url:String
    private val gson = Gson()
    private val questionsList = ArrayList<News>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        url =
            "https://newsapi.org/v2/top-headlines?country=in&category=${arguments?.getString("CATEGORY")}&apiKey=73ae6891ccd2401f92adb0b8360fc88e"
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    // Function to make the API call and fetch the questions related to the tag searched by the user
    private fun fetchQuestions() {
        Log.e("TAG",url)
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                client.newCall(request).enqueue(this)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: ResponseBody? = response.body()
                val result = responseBody?.string()
                Log.e(javaClass.name,result)
                val newsResponse = gson.fromJson(result, NewsResponse::class.java)
                questionsList.addAll(newsResponse.articles)
                activity?.runOnUiThread {
                    rvNews.visibility = View.VISIBLE
                    pbLoading.visibility = View.GONE
                    newsAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNews.visibility = View.GONE
        newsAdapter =
            NewsAdapter(questionsList, arguments!!.getInt("COLOR"))
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        rvNews.adapter = newsAdapter
        fetchQuestions()
    }
}
