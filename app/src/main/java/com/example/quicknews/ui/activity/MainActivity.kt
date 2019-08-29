package com.example.quicknews.ui.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.quicknews.ui.fragment.NewsFragment
import com.example.quicknews.R
import com.example.quicknews.ui.fragment.FavouriteFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var color: Int = Color.parseColor("#FF9800")
    private var darkColor: Int = Color.parseColor("#F57C00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val colorDrawable = ColorDrawable(color)
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = darkColor
        }

        checkNetworkStatus()

        val fragment = NewsFragment.newInstance("general", color)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        lateinit var fragment: Fragment
        lateinit var title: String

        when (item.itemId) {

            R.id.nav_general -> {
                color = Color.parseColor("#FF9800")
                darkColor = Color.parseColor("#F57C00")
                fragment =
                    NewsFragment.newInstance("general", color)
                title = "Quick News"
            }

            R.id.nav_business -> {
                color = Color.parseColor("#FFEB3B")
                darkColor = Color.parseColor("#FBC02D")
                fragment =
                    NewsFragment.newInstance("business", color)
                title = "Business"
            }

            R.id.nav_entertainment -> {
                color = Color.parseColor("#03A9F4")
                darkColor = Color.parseColor("#0288D1")
                fragment =
                    NewsFragment.newInstance("entertainment", color)
                title = "Entertainment"
            }

            R.id.nav_health -> {
                color = Color.parseColor("#FF4081")
                darkColor = Color.parseColor("#E91E63")
                fragment = NewsFragment.newInstance("health", color)
                title = "Health"
            }

            R.id.nav_science -> {
                color = Color.parseColor("#8BC34A")
                darkColor = Color.parseColor("#689F38")
                fragment =
                    NewsFragment.newInstance("science", color)
                title = "Science"
            }

            R.id.nav_sports -> {
                color = Color.parseColor("#4CAF50")
                darkColor = Color.parseColor("#388E3C")
                fragment = NewsFragment.newInstance("sports", color)
                title = "Sports"
            }

            R.id.nav_technology -> {
                color = Color.parseColor("#9C27B0")
                darkColor = Color.parseColor("#7B1FA2")
                fragment =
                    NewsFragment.newInstance("technology", color)
                title = "Technology"
            }

            R.id.nav_favourite -> {
                color = Color.parseColor("#9C27B0")
                darkColor = Color.parseColor("#7B1FA2")
                fragment = FavouriteFragment.newInstance(color)
                title = "Favourite"
            }
        }

        checkNetworkStatus()

        //Set action bar title based on the category of news opened
        supportActionBar?.title = title

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()

        //Set actionBar color based on category of news
        val colorDrawable = ColorDrawable(color)
        supportActionBar?.setBackgroundDrawable(colorDrawable)
        //Changes the color of status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = darkColor
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun checkNetworkStatus() {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        val isConnected = networkInfo != null && networkInfo.isConnected
        if (!isConnected) {
            showSimpleAlert()
        }
    }

    private fun showSimpleAlert() {
        val simpleAlert = AlertDialog.Builder(this)
            .setTitle("No Internet Connection")
            .setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
            .setCancelable(false)
            .create()
        simpleAlert.show()
    }

}
