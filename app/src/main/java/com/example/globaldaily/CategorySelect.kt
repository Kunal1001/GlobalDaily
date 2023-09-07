package com.example.globaldaily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CategorySelect : AppCompatActivity() {
    private lateinit var intent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_select)
        val key = "1"
        var url:String = ""
        findViewById<Button>(R.id.ind).setOnClickListener {
            intent = Intent(this , Home::class.java)
            url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=e4e959f44d154514963aa96ea00cf576"
            intent.putExtra(key,url)
            startActivity(intent)
        }
        findViewById<Button>(R.id.tech).setOnClickListener {
            intent = Intent(this , Home::class.java)
            url = "https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=e4e959f44d154514963aa96ea00cf576"
            intent.putExtra(key,url)
            startActivity(intent)
        }
        findViewById<Button>(R.id.business).setOnClickListener {
            intent = Intent(this , Home::class.java)
            url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=e4e959f44d154514963aa96ea00cf576"
            intent.putExtra(key,url)
            startActivity(intent)
        }
        findViewById<Button>(R.id.science).setOnClickListener {
            intent = Intent(this , Home::class.java)
            url = "https://newsapi.org/v2/top-headlines?country=us&category=science&apiKey=e4e959f44d154514963aa96ea00cf576"
            intent.putExtra(key,url)
            startActivity(intent)
        }
        findViewById<Button>(R.id.sports).setOnClickListener {
            intent = Intent(this , Home::class.java)
            url = "https://newsapi.org/v2/top-headlines?category=sports&country=in&apiKey=e4e959f44d154514963aa96ea00cf576"
            intent.putExtra(key,url)
            startActivity(intent)
        }
    }
}