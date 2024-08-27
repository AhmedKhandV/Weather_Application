package com.example.kotlinweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinweather.domain.GetCurrentWeatherUseCase
import com.example.kotlinweather.domain.GetWeatherByCityUseCase
import com.example.kotlinweather.domain.LocationService
import com.example.kotlinweather.domain.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val locationService: LocationService
) : ViewModel() {

    private val _weatherResponse = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherResponse: StateFlow<WeatherUiState> = _weatherResponse

    fun getCurrentWeather() {
        viewModelScope.launch {
            _weatherResponse.value = WeatherUiState.Loading
            try {
                val locationResult = locationService.getCurrentLocation()
                locationResult.fold(
                    onSuccess = { (latitude, longitude) ->
                        getCurrentWeatherUseCase(latitude, longitude)
                            .catch { e ->
                                _weatherResponse.value = WeatherUiState.Error(e.localizedMessage ?: "An error occurred")
                            }
                            .collect { result ->
                                result.fold(
                                    onSuccess = { weather ->
                                        _weatherResponse.value = WeatherUiState.Success(weather)
                                    },
                                    onFailure = { e ->
                                        _weatherResponse.value = WeatherUiState.Error(e.localizedMessage ?: "An error occurred")
                                    }
                                )
                            }
                    },
                    onFailure = { e ->
                        _weatherResponse.value = WeatherUiState.Error("Error fetching location: ${e.localizedMessage}")
                    }
                )
            } catch (e: Exception) {
                _weatherResponse.value = WeatherUiState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    fun getWeatherByCity(cityName: String) {
        viewModelScope.launch {
            _weatherResponse.value = WeatherUiState.Loading
            getWeatherByCityUseCase(cityName)
                .catch { e ->
                    _weatherResponse.value = WeatherUiState.Error(e.localizedMessage ?: "An error occurred")
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { weather ->
                            _weatherResponse.value = WeatherUiState.Success(weather)
                        },
                        onFailure = { e ->
                            _weatherResponse.value = WeatherUiState.Error(e.localizedMessage ?: "An error occurred")
                        }
                    )
                }
        }
    }
}
