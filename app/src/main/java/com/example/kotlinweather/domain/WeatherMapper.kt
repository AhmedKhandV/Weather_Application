package com.example.kotlinweather.domain

import com.example.kotlinweather.data.WeatherResponse

// WeatherMapper.kt
fun WeatherResponse.toDomainModel(): Weather {
    return Weather(
        id = id,
        cityName = name,
        temperature = main.temp,
        feelsLike = main.feels_like,
        humidity = main.humidity,
        pressure = main.pressure,
        windSpeed = wind.speed,
        description = weather.firstOrNull()?.description ?: "",
        icon = weather.firstOrNull()?.icon ?: ""
    )
}