package com.example.weatherapp.city_weather

import com.example.weatherapp.city_forecast.*
import com.example.weatherapp.search_cities_data.Coord
import com.example.weatherapp.search_cities_data.Main
import com.squareup.moshi.Json

data class CityWeatherResponse(
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "weather")
    val weather: List<WeatherInfo>,
    @Json(name = "base")
    val base: String,
    @Json(name = "main")
    val main: Main,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "wind")
    val wind: WindInfo,
    @Json(name = "clouds")
    val clouds: CloudsInfo,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "sys")
    val sys: SysInfo,
    @Json(name = "timezone")
    val timezone: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "cod")
    val cod: Int,

)
