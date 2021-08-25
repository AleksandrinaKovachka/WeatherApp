package com.example.weatherapp.city_forecast

import com.squareup.moshi.Json

data class CityForecast(
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "main")
    val main: LifeInfo,
    @Json(name = "weather")
    val weather: List<WeatherInfo>,
    @Json(name = "clouds")
    val clouds: CloudsInfo,
    @Json(name = "wind")
    val wind: WindInfo,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "pop")
    val pop: Double,
    @Json(name = "sys")
    val sys: SysPod,
    @Json(name = "dt_txt")
    val dtTxt: String
)
