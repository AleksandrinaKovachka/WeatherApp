package com.example.weatherapp.search_cities_data

import com.squareup.moshi.Json

data class Clouds(
    @Json(name = "all")
    val all: Int
)