package io.minoro75.miniweatherapp.ui.hourly_weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.minoro75.miniweatherapp.data.Hourly
import io.minoro75.miniweatherapp.repository.WeatherRepository
import io.minoro75.miniweatherapp.utils.NetworkUtils
import io.minoro75.miniweatherapp.utils.Resource
import kotlinx.coroutines.launch
import kotlin.Exception

class HourlyViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository,
    networkUtils: NetworkUtils
) : ViewModel() {

    private val _hourlyForecast = MutableLiveData<Resource<List<Hourly>>>()
    val hourlyForecast: LiveData<Resource<List<Hourly>>> = _hourlyForecast

    init {
        _hourlyForecast.postValue(Resource.loading(null))

        if (networkUtils.isNetworkConnected()) {
            fetchWeather()
        } else {
            _hourlyForecast.postValue(Resource.error(null, "internet error"))
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                _hourlyForecast.value =
                    Resource.success(data = ArrayList(weatherRepository.getWeatherInAntalya().hourly))


            } catch (ex: Exception) {
                _hourlyForecast.value =
                    Resource.error(data = null, message = ex.localizedMessage ?: "unexpected error")
            }
        }
    }


}