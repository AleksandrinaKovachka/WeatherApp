package com.example.weatherapp.data


import com.google.gson.annotations.SerializedName

data class SearchCitiesResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("list")
    val list: List<Country>
)