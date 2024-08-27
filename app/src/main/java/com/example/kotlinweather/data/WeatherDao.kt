package com.example.kotlinweather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE lat = :lat AND lon = :lon")
    suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherEntity?

    @Query("SELECT * FROM weather WHERE cityName = :cityName")
    suspend fun getWeatherByCity(cityName: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
}
