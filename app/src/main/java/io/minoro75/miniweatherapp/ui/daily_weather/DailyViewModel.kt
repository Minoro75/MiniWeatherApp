package io.minoro75.miniweatherapp.ui.daily_weather

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.minoro75.miniweatherapp.repository.WeatherRepository
import io.minoro75.miniweatherapp.utils.NetworkUtils

class DailyViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository,
    networkUtils: NetworkUtils
) : ViewModel() {

}