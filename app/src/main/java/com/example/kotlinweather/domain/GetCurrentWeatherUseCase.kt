package com.example.kotlinweather.domain

import com.example.kotlinweather.data.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(latitude: Double, longitude: Double): Flow<Result<Weather>> = flow {
        try {
            val weatherResponse = weatherRepository.getCurrentWeather(latitude, longitude)
            emit(Result.success(weatherResponse.toDomainModel()))
        } catch (e: Exception) {
            emit(Result.failure(mapToWeatherException(e)))
        }
    }

    private fun mapToWeatherException(e: Exception): WeatherException {
        return when (e) {
            is IOException -> WeatherException.NetworkException("Network error: ${e.message}")
            is HttpException -> WeatherException.ApiException("API error: ${e.message}")
            else -> WeatherException.ApiException("Unknown error: ${e.message}")
        }
    }
}
