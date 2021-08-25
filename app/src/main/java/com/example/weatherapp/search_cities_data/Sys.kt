package com.example.weatherapp.search_cities_data

import com.squareup.moshi.Json

data class Sys(
    @Json(name = "country")
    val country: String
)