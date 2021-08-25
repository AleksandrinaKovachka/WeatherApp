package com.example.weatherapp.city_forecast

import com.example.weatherapp.search_cities_data.Coord
import com.squareup.moshi.Json

data class CityInfo(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "country")
    val country: String,
    @Json(name = "population")
    val population: Int,
    @Json(name = "timezone")
    val timezone: Int,
    @Json(name = "sunrise")
    val sunrise: Int,
    @Json(name = "sunset")
    val sunset: Int
)
