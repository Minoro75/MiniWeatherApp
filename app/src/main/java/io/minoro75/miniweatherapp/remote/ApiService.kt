package io.minoro75.miniweatherapp.remote

import io.minoro75.miniweatherapp.data.Weather
import retrofit2.http.GET

interface ApiService {
    @GET(RestConfig.API_ONE_CALL + RestConfig.API_TOKEN)
    suspend fun getAntalyaWeather(): Weather
}