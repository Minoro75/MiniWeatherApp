package io.minoro75.miniweatherapp.remote

import io.minoro75.miniweatherapp.data.Weather
import javax.inject.Inject

class ApiServiceHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiServiceHelper {
    override suspend fun getLondonWeather(): Weather {
        return apiService.getLondonWeather()
    }
}