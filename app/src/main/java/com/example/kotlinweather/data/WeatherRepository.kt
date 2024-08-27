package com.example.kotlinweather.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
    private val weatherDao: WeatherDao
) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponse {
        return withContext(Dispatchers.IO) {
            val cachedWeather = weatherDao.getWeatherByCoordinates(lat, lon)
            if (cachedWeather != null && !isCacheExpired(cachedWeather.timestamp)) {
                cachedWeather.toWeatherResponse()
            } else {
                val freshWeather = api.getCurrentWeather(lat, lon)
                weatherDao.insertWeather(freshWeather.toWeatherEntity(lat, lon))
                freshWeather
            }
        }
    }

    suspend fun getWeatherByCity(cityName: String): WeatherResponse {
        return withContext(Dispatchers.IO) {
            val cachedWeather = weatherDao.getWeatherByCity(cityName)
            if (cachedWeather != null && !isCacheExpired(cachedWeather.timestamp)) {
                cachedWeather.toWeatherResponse()
            } else {
                val freshWeather = api.getWeatherByCity(cityName)
                weatherDao.insertWeather(freshWeather.toWeatherEntity(cityName = cityName))
                freshWeather
            }
        }
    }

    private fun isCacheExpired(timestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        val cacheLifetime = 30 * 60 * 1000 // 30 minutes
        return currentTime - timestamp > cacheLifetime
    }
}
