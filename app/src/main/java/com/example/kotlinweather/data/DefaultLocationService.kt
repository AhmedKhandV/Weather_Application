package com.example.kotlinweather.data

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.kotlinweather.domain.LocationService
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultLocationService(private val context: Context) : LocationService {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override suspend fun getCurrentLocation(): Result<Pair<Double, Double>> = withContext(Dispatchers.IO) {
        return@withContext try {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                android.content.pm.PackageManager.PERMISSION_GRANTED) {
                Result.failure(SecurityException("Location permission not granted"))
            } else {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    Result.success(Pair(location.latitude, location.longitude))
                } else {
                    Result.failure(Exception("Location not available"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}