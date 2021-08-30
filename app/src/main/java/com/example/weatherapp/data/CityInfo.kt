package com.example.weatherapp.data

import java.io.Serializable

data class CityInfo(val id: Int = 0, val name: String, val country: String, val temp: Int, val img: String) : Serializable
