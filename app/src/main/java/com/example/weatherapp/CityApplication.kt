package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.database.AppDatabase

class CityApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}