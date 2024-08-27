package com.example.kotlinweather.presentation

import com.example.kotlinweather.domain.Weather

// WeatherUiState.kt
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weather: Weather) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}