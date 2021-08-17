package com.example.weatherapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CityListViewModel(private val cityDao: CityDao): ViewModel() {
    fun allCities(): Flow<List<CityData>> = cityDao.getAll()

    fun insert(name: String, id: String) = viewModelScope.launch {
        cityDao.insertToRoomDatabase(CityData(name, id))
    }

    fun delete(name: String, id: String) = viewModelScope.launch {
        cityDao.deleteCity(CityData(name, id))
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