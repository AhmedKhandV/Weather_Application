package com.example.kotlinweather.domain

interface LocationService {
    suspend fun getCurrentLocation(): Result<Pair<Double, Double>>

}