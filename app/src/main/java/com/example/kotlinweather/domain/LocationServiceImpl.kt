package com.example.kotlinweather.domain

import javax.inject.Inject

class LocationServiceImpl @Inject constructor() : LocationService {
    override suspend fun getCurrentLocation(): Result<Pair<Double, Double>> {
        // Implement location fetching logic here.
        return Result.success(Pair(0.0, 0.0)) // Replace with actual location fetching logic
    }
}
