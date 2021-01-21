package io.minoro75.miniweatherapp.ui.current_weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.minoro75.miniweatherapp.data.Weather
import io.minoro75.miniweatherapp.repository.WeatherRepository
import io.minoro75.miniweatherapp.utils.NetworkUtils
import io.minoro75.miniweatherapp.utils.Resource
import kotlinx.coroutines.launch
import kotlin.Exception

class CurrentViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository,
    networkUtils: NetworkUtils
) : ViewModel() {
    private val _weather = MutableLiveData<Resource<Weather>>()
    val weather: LiveData<Resource<Weather>> = _weather

    private val _lat = MutableLiveData<Double>()
    val lat: LiveData<Double> = _lat

    private val _lon = MutableLiveData<Double>()
    val lon: LiveData<Double> = _lon


    init {
        _weather.postValue(Resource.loading(null))

        if (networkUtils.isNetworkConnected()) {
            getWeatherInLocation(36.8969, 30.7133)
        } else {
            _weather.postValue(Resource.error(data = null, message = "Internet Error"))
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                _weather.value = Resource.success(data = weatherRepository.getWeatherInAntalya())

            } catch (ex: Exception) {
                _weather.value =
                    Resource.error(data = null, message = ex.localizedMessage ?: "unexpected error")
            }
        }
    }

    fun getWeatherInLocation(lat: Double, lon: Double) {
        _lat.value = lat
        _lon.value = lon

        viewModelScope.launch {
            try {
                _weather.value =
                    Resource.success(data = weatherRepository.getWeatherInLocation(lat, lon))
            } catch (ex: Exception) {
                _weather.value =
                    Resource.error(data = null, message = ex.localizedMessage ?: "unexpected error")
            }
        }

    }

}
