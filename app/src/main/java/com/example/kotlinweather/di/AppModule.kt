package com.example.kotlinweather.di

import android.content.Context
import com.example.kotlinweather.data.*
import com.example.kotlinweather.domain.GetCurrentWeatherUseCase
import com.example.kotlinweather.domain.GetWeatherByCityUseCase
import com.example.kotlinweather.domain.LocationService
import com.example.kotlinweather.data.DefaultLocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideLocationService(@ApplicationContext context: Context): LocationService {
        return DefaultLocationService(context)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        weatherDao: WeatherDao
    ): WeatherRepository {
        return WeatherRepository(api, weatherDao)
    }

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(
        weatherRepository: WeatherRepository
    ): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(weatherRepository)
    }

    @Provides
    @Singleton
    fun provideGetWeatherByCityUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherByCityUseCase {
        return GetWeatherByCityUseCase(weatherRepository)
    }
}
