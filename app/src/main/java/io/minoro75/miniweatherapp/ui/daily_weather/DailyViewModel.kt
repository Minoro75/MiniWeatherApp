package io.minoro75.miniweatherapp.ui.daily_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is daily forecast Fragment"
    }
    val text: LiveData<String> = _text
}