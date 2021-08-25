package com.example.weatherapp.city_forecast

import com.example.weatherapp.search_cities_data.Country
import com.squareup.moshi.Json

data class CityForecastResponse(
    @Json(name = "cod")
    val cod: String,
    @Json(name = "message")
    val message: String,
    @Json(name = "count")
    val count: Int,
    @Json(name = "list")
    val list: List<CityForecast>,
    @Json(name = "city")
    val city: CityInfo
)
