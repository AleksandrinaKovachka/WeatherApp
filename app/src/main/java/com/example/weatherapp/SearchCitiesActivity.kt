package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.TextView

class SearchCitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cities)

        val cityName = intent.getStringExtra("city_name")

        val cityNameTextView = findViewById<TextView>(R.id.cityNameTextView)
        cityNameTextView.text = cityName
    }
}