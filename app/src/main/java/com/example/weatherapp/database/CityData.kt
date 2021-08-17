package com.example.weatherapp.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_data")
data class CityData (
    @PrimaryKey @NonNull @ColumnInfo(name = "city_id") val cityId: String,
    @NonNull @ColumnInfo(name = "city_name") val cityName: String
)