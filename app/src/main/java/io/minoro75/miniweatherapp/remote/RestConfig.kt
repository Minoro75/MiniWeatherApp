package io.minoro75.miniweatherapp.remote

class RestConfig {
    // may hide this obj later, but now its open to prove app is working
    companion object {
        const val API_TOKEN = "07eeef1641ebdea4f753811d54cfcddd"
        const val API_SERVER = "http://api.openweathermap.org"
        const val API_LONDON_WEATHER = "/data/2.5/weather?q=London&appid="

    }
}