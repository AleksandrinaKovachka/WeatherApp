package com.example.weatherapp.city_weather

import com.example.weatherapp.search_cities_data.Coord
import com.squareup.moshi.Json

data class SysInfo(
    @Json(name = "country")
    val country: String,
    @Json(name = "sunrise")
    val sunrise: Long,
    @Json(name = "sunset")
    val sunset: Long
)
