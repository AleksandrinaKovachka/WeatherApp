package com.example.weatherapp.search_cities_data

import com.squareup.moshi.Json

data class Wind(
    @Json(name = "speed")
    val speed: Double,
    @Json(name = "deg")
    val deg: Int
)