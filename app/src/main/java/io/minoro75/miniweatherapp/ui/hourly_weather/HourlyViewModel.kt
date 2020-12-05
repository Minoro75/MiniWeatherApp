package io.minoro75.miniweatherapp.ui.hourly_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HourlyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is hourly weather Fragment"
    }
    val text: LiveData<String> = _text
}