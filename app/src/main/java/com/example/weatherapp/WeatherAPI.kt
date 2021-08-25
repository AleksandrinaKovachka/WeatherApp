package com.example.weatherapp

import com.example.weatherapp.city_forecast.CityForecastResponse
import com.example.weatherapp.city_weather.CityWeatherResponse
import com.example.weatherapp.search_cities_data.SearchCitiesResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

class WeatherAPI {
    object Service {
        val retrofit: WeatherService by lazy {
            Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
        }
    }

    interface WeatherService {
        @GET("find")
        suspend fun getSearchedCities(@QueryMap map: Map<String, String>): SearchCitiesResponse

        @GET("forecast")
        suspend fun getWeatherForecast(@QueryMap map: Map<String, String>): CityForecastResponse

        @GET("weather")
        suspend fun getCityWeather(@QueryMap map: Map<String, String>): CityWeatherResponse
    }
}

