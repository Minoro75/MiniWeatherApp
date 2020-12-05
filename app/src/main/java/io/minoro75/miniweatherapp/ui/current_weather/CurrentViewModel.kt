package io.minoro75.miniweatherapp.ui.current_weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.minoro75.miniweatherapp.data.Main
import io.minoro75.miniweatherapp.data.Weather
import io.minoro75.miniweatherapp.repository.WeatherRepository
import io.minoro75.miniweatherapp.utils.NetworkUtils
import io.minoro75.miniweatherapp.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class CurrentViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository,
    networkUtils: NetworkUtils
) : ViewModel() {
    private val _weather = MutableLiveData<Resource<Weather>>()
    val weather: LiveData<Resource<Weather>> = _weather

    private val _weatherMain = MutableLiveData<Resource<Main>>()
    val weatherMain: LiveData<Resource<Main>> = _weatherMain


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
                _weather.value = Resource.success(data = weatherRepository.getWeatherInLondon())
                _weatherMain.value =
                    Resource.success(data = weatherRepository.getWeatherInLondon().main)
            } catch (ex: Exception) {
                _weather.value =
                    Resource.error(data = null, message = ex.localizedMessage ?: "unexpected error")
            }
        }
    }


}