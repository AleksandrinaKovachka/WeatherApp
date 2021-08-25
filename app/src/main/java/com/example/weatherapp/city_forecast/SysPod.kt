package com.example.weatherapp.city_forecast

import com.squareup.moshi.Json

data class SysPod(
    @Json(name = "pod")
    val pod: String
)