package io.minoro75.miniweatherapp.repository

import io.minoro75.miniweatherapp.data.Weather
import io.minoro75.miniweatherapp.remote.ApiServiceHelper
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiServiceHelper: ApiServiceHelper
) {

    suspend fun getWeatherInAntalya(): Weather {
        return apiServiceHelper.getAntalyaWeather()
    }

    suspend fun getWeatherInLocation(lat: Double, lon: Double): Weather {
        return apiServiceHelper.getWeatherInLocation(lat, lon)
    }

}