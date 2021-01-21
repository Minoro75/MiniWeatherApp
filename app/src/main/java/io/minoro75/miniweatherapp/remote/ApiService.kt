package io.minoro75.miniweatherapp.remote

import io.minoro75.miniweatherapp.data.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(RestConfig.API_ONE_CALL + RestConfig.API_TOKEN)
    suspend fun getAntalyaWeather(): Weather

    @GET("/data/2.5/onecall?&exclude=minutely,alerts&units=metric&appid=07eeef1641ebdea4f753811d54cfcddd")
    suspend fun getWeatherInLocation(
        @Query(value = "lat") lat: Double,
        @Query(value = "lon") lon: Double
    ): Weather

}