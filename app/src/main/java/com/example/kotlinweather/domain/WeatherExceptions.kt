package com.example.kotlinweather.domain

// WeatherExceptions.kt
sealed class WeatherException(message: String) : Exception(message) {
    class NetworkException(message: String) : WeatherException(message)
    class LocationNotFoundException(message: String) : WeatherException(message)
    class ApiException(message: String) : WeatherException(message)
}