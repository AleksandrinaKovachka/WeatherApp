package com.example.weatherapp.city_forecast

import com.squareup.moshi.Json

data class LifeInfo(
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "temp_min")
    val temp_min: Double,
    @Json(name = "temp_max")
    val temp_max: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "sea_level")
    val seaLevel: Int,
    @Json(name = "grnd_level")
    val grndLevel: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "temp_kf")
    val tempKf: Int
)
