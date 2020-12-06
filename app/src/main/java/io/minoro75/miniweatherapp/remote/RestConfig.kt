package io.minoro75.miniweatherapp.remote

class RestConfig {
    // may hide this obj later, but now its open to prove app is working
    companion object {
        const val API_TOKEN =
            "?lat=36.8969&lon=30.7133&exclude=minutely,alerts&units=metric&appid=07eeef1641ebdea4f753811d54cfcddd"
        const val API_SERVER = "https://api.openweathermap.org"
        const val API_ONE_CALL = "/data/2.5/onecall"

    }
}