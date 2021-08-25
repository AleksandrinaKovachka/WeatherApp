package com.example.weatherapp.search_cities_data

import com.squareup.moshi.Json

data class SearchCitiesResponse(
    @Json(name = "message")
    val message: String,
    @Json(name = "cod")
    val cod: String,
    @Json(name = "count")
    val count: Int,
    @Json(name = "list")
    val list: List<Country>
)