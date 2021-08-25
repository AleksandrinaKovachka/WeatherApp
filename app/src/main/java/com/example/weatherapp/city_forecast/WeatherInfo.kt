package com.example.weatherapp.city_forecast

import com.squareup.moshi.Json

data class WeatherInfo(
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String
)
