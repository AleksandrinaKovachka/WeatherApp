package com.example.weatherapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherAPI
import com.example.weatherapp.city_forecast.CityForecastResponse
import com.example.weatherapp.city_weather.CityWeatherResponse
import com.example.weatherapp.search_cities_data.SearchCitiesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

const val APP_ID = "9405140ec7fd6dc8882d7e44f40da75c"

class CityListViewModel(private val cityDao: CityDao) : ViewModel() {
    fun allCities(): Flow<List<CityData>> = cityDao.getAll()

    fun insert(id: Int, name: String, country: String) = viewModelScope.launch {
        cityDao.insertToRoomDatabase(CityData(id, name, country))
    }

    fun delete(id: Int, name: String, country: String) = viewModelScope.launch {
        cityDao.deleteCity(CityData(id, name, country))
    }

    fun getCitiesByName(name: String): Flow<SearchCitiesResponse> {
        val map = HashMap<String, String>()
        map.apply {
            put("appId", APP_ID)
            put("q", name)
        }

        return flow {
            val result = WeatherAPI.Service.retrofit.getSearchedCities(map)
            emit(result)
        }
    }

    fun getWeatherForecastByCityId(id: Int): Flow<CityForecastResponse> {
        val map = HashMap<String, String>()
        map.apply {
            put("appId", APP_ID)
            put("id", id.toString())
            put("units", "metric")
        }

        return flow {
            val result = WeatherAPI.Service.retrofit.getWeatherForecast(map)
            emit(result)
        }
    }

    fun getCityWeatherByCityId(id: Int): Flow<CityWeatherResponse> {
        val map = HashMap<String, String>()
        map.apply {
            put("appId", APP_ID)
            put("id", id.toString())
            put("units", "metric")
        }

        return flow {
            val result = WeatherAPI.Service.retrofit.getCityWeather(map)
            emit(result)
        }
    }
}

class CityListViewModelFactory(private val cityDao: CityDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CityListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CityListViewModel(cityDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}