package com.example.kotlinweather.domain

data class Weather(
    val id: Int,
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String
)
