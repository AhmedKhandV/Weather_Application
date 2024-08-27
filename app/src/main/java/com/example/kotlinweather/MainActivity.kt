package com.example.kotlinweather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.kotlinweather.domain.GetCurrentWeatherUseCase
import com.example.kotlinweather.domain.LocationService
import com.example.kotlinweather.domain.Weather
import com.example.kotlinweather.presentation.WeatherUiState
import com.example.kotlinweather.presentation.WeatherViewModel
import com.example.weather.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Inject
    lateinit var locationService: LocationService

    private lateinit var search: EditText
    private lateinit var searchButton: View
    private lateinit var weatherViewModel: WeatherViewModel

    private val requestLocationPermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            fetchCurrentWeather()
        } else {
            showError("Location permission is required")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        search = findViewById(R.id.cityInput)
        searchButton = findViewById(R.id.searchButton)

        // Initialize ViewModel
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        // Set up ViewModel observer
        lifecycleScope.launch {
            weatherViewModel.weatherResponse.collect { state ->
                when (state) {
                    is WeatherUiState.Loading -> {
                        // Optionally show loading spinner or similar
                        findViewById<TextView>(R.id.errorText).visibility = View.GONE
                    }
                    is WeatherUiState.Success -> {
                        updateUIWithWeather(state.weather)
                    }
                    is WeatherUiState.Error -> {
                        showError(state.message)
                    }
                }
            }
        }

        // Set up click listener for search button
        searchButton.setOnClickListener {
            val cityName = search.text.toString().trim()
            if (cityName.isNotEmpty()) {
                weatherViewModel.getWeatherByCity(cityName)
            } else {
                showError("Please enter a city name")
            }
        }

        if (checkLocationPermission()) {
            fetchCurrentWeather()
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun fetchCurrentWeather() {
        weatherViewModel.getCurrentWeather()
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun updateUIWithWeather(weather: Weather) {
        runOnUiThread {
            findViewById<TextView>(R.id.cityName).text = weather.cityName
            findViewById<TextView>(R.id.temperature).text = "${weather.temperature}°C"
            findViewById<TextView>(R.id.description).text = weather.description
            findViewById<TextView>(R.id.humidity).text = "Humidity: ${weather.humidity}%"
            findViewById<TextView>(R.id.windSpeed).text = "Wind: ${weather.windSpeed} m/s"

            val weatherIconUrl = "https://openweathermap.org/img/wn/${weather.icon}.png"
            findViewById<ImageView>(R.id.weatherIcon).let {
                Glide.with(this)
                    .load(weatherIconUrl)
                    .into(it)
            }

            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            findViewById<TextView>(R.id.errorText).apply {
                visibility = View.VISIBLE
                text = message
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}
