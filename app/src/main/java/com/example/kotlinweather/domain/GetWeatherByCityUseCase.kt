package com.example.kotlinweather.domain

import com.example.kotlinweather.data.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(cityName: String): Flow<Result<Weather>> = flow {
        try {
            val weatherResponse = weatherRepository.getWeatherByCity(cityName)
            emit(Result.success(weatherResponse.toDomainModel()))
        } catch (e: Exception) {
            emit(Result.failure(mapToWeatherException(e)))
        }
    }

    private fun mapToWeatherException(e: Exception): WeatherException {
        return when (e) {
            is IOException -> WeatherException.NetworkException("Network error: ${e.message}")
            is HttpException -> {
                if (e.code() == 404) WeatherException.LocationNotFoundException("City not found")
                else WeatherException.ApiException("API error: ${e.message()}")
            }
            else -> WeatherException.ApiException("Unknown error: ${e.message}")
        }
    }
}