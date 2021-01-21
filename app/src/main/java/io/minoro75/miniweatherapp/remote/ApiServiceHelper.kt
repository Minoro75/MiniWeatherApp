package io.minoro75.miniweatherapp.remote

import io.minoro75.miniweatherapp.data.Weather

// Wrapped with Resource Type to offer:
// loading, success and error

interface ApiServiceHelper {
    suspend fun getAntalyaWeather(): Weather
    suspend fun getWeatherInLocation(lat: Double, lon: Double): Weather
}