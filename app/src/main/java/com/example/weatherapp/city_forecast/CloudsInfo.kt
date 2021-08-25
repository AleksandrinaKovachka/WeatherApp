package com.example.weatherapp.city_forecast

import com.squareup.moshi.Json

data class CloudsInfo(
    @Json(name = "all")
    val all: Int,
)
