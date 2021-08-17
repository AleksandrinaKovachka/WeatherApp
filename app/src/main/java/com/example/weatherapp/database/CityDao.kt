package com.example.weatherapp.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM city_data ORDER BY city_id")
    fun getAll(): Flow<List<CityData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToRoomDatabase(cityData: CityData)

    @Delete
    suspend fun deleteCity(cityData: CityData)
}