package com.example.kotlinweather.data

fun WeatherResponse.toWeatherEntity(lat: Double? = null, lon: Double? = null, cityName: String? = null): WeatherEntity {
    return WeatherEntity(
        id = id,
        cityName = cityName ?: name,
        lat = lat ?: coord.lat,
        lon = lon ?: coord.lon,
        temperature = main.temp,
        feelsLike = main.feels_like,
        humidity = main.humidity,
        pressure = main.pressure,
        windSpeed = wind.speed,
        description = weather.firstOrNull()?.description ?: "",
        icon = weather.firstOrNull()?.icon ?: ""
    )
}

fun WeatherEntity.toWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        coord = Coord(lon, lat),
        weather = listOf(Weather(0, "", description, icon)),
        base = "",
        main = Main(temperature, feelsLike, temperature, temperature, pressure, humidity),
        visibility = 0,
        wind = Wind(windSpeed, 0),
        clouds = Clouds(0),
        dt = timestamp / 1000,
        sys = Sys(0, 0, "", 0, 0),
        timezone = 0,
        id = id,
        name = cityName,
        cod = 200
    )
}