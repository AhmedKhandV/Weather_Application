package com.example.kotlinweather.domain

import com.example.kotlinweather.data.WeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double): WeatherResponse
    suspend fun getWeatherByCity(cityName: String): WeatherResponse
}