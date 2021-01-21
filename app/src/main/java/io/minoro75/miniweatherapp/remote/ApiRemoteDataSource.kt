package io.minoro75.miniweatherapp.remote

import io.minoro75.miniweatherapp.data.Weather
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : ApiServiceHelper {
    override suspend fun getAntalyaWeather(): Weather {
        return apiService.getAntalyaWeather()
    }

    override suspend fun getWeatherInLocation(lat: Double, lon: Double): Weather {
        return apiService.getWeatherInLocation(lat = lat, lon = lon)
    }
}