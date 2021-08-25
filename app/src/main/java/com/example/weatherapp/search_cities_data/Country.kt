package com.example.weatherapp.search_cities_data

import com.squareup.moshi.Json

data class Country(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "main")
    val main: Main,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "rain")
    val rain: Any,
    @Json(name = "snow")
    val snow: Any,
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "weather")
    val weather: List<Weather>
)