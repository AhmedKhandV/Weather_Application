package com.example.kotlinweather.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    companion object {
        const val WEATHER_API_KEY = "10700df7ecc912843e1a515df66a477a"
    }

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = WEATHER_API_KEY
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = WEATHER_API_KEY
    ): WeatherResponse
}