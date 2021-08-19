package com.example.weatherapp

import com.example.weatherapp.data.SearchCitiesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/find?q=name&appid=app_id")
    fun getSearchedCities(@Query("name") name: String, @Query("app_id") app_id: String): Call<SearchCitiesResponse>

}