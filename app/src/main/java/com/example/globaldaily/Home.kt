package com.example.globaldaily

import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request.Method.GET
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : AppCompatActivity() {
    private lateinit var mAdapter: NewsListAdapter
    private lateinit var newsArray: ArrayList<News>
    private var url = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=e4e959f44d154514963aa96ea00cf576"
    private var url2:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpRecyclerView()
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this,CategorySelect::class.java)
            startActivity(intent)
        }

    }
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //***Change Here***

            startActivity(intent)
            finish()
            System.exit(0)

        }
    }
    private fun fetchData(){
        if(url2.contains("https")) url = url2
        val jsonObjectRequest = object:JsonObjectRequest(
            GET, url, null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("publishedAt"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.update(newsArray)
            },
            { error: VolleyError ->
                Log.e("TAG", error.toString())
                Toast.makeText(this,"something went wrong", Toast.LENGTH_LONG).show()
            }


        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    private fun setUpRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val LinearLayoutManager = object : LinearLayoutManager(this) { override fun canScrollVertically() = false }
        recyclerView.layoutManager = LinearLayoutManager
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
        url2 =  intent.getStringExtra("1").toString()
        fetchData()

        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        if (position + 1 < newsArray.size) {
                            newsArray.removeAt(position)
                            mAdapter.updatePop(position)
                            window.decorView.apply {
                                systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                            }
                        }
                        else{
                            makeToast()
                            setUpRecyclerView()
                        }
                    }
                    ItemTouchHelper.RIGHT -> {
                        onItemClicked(newsArray[viewHolder.adapterPosition])
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
        mAdapter.update(newsArray)

    }
    fun makeToast(){
        Toast.makeText(this,"Going back to top",Toast.LENGTH_LONG).show()
    }



}