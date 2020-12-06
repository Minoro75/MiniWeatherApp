package io.minoro75.miniweatherapp.ui.hourly_weather

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.minoro75.miniweatherapp.data.Hourly
import io.minoro75.miniweatherapp.data.Weather
import io.minoro75.miniweatherapp.repository.WeatherRepository
import io.minoro75.miniweatherapp.utils.NetworkUtils
import io.minoro75.miniweatherapp.utils.Resource
import kotlinx.coroutines.launch
import kotlin.Exception

class HourlyViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository,
    networkUtils: NetworkUtils
) : ViewModel() {
    private val _weather = MutableLiveData<Resource<Weather>>()
    val weather: LiveData<Resource<Weather>> = _weather

    private val _hourly = MutableLiveData<ArrayList<Hourly>>()
    val hourly: LiveData<ArrayList<Hourly>> = _hourly

    private val _hourlyForecast = MutableLiveData<Resource<List<Hourly>>>()
    val hourlyForecast: LiveData<Resource<List<Hourly>>> = _hourlyForecast

    init {
        _weather.postValue(Resource.loading(null))

        if (networkUtils.isNetworkConnected()) {
            fetchWeather()
        } else {
            _weather.postValue(Resource.error(data = null, message = "Internet Error"))
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                _weather.value = Resource.success(data = weatherRepository.getWeatherInAntalya())
                _hourly.value = ArrayList(_weather.value!!.data!!.hourly)
                _hourlyForecast.value =
                    Resource.success(data = ArrayList(weatherRepository.getWeatherInAntalya().hourly))


            } catch (ex: java.lang.Exception) {
                _weather.value =
                    Resource.error(data = null, message = ex.localizedMessage ?: "unexpected error")
            }
        }
    }


}