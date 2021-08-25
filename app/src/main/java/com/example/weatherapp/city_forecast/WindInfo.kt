package com.example.weatherapp.city_forecast

import com.squareup.moshi.Json

data class WindInfo(
    @Json(name = "speed")
    val speed: Double,
    @Json(name = "deg")
    val deg: Int,
    @Json(name = "gust")
    val gust: Double
)
