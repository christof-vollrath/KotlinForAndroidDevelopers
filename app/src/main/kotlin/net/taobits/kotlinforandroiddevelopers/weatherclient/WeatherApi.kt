package net.taobits.kotlinforandroiddevelopers.weatherclient

import com.google.gson.Gson
import java.net.URL

data class Coordinates(val long: Float, val lat: Float)
data class City(val id: Long, val name: String, val coord: Coordinates, val country: String, val population: Int)
data class Temparature(val day: Float, val min: Float, val max: Float, val night: Float, val eve: Float, val morn: Float)
data class Weather(val id: Long, val main: String, val description: String, val icon: String)


data class Forecast(val dt: Long, val temp: Temparature, val pressure: Float, val humidity: Int, val weather: List<Weather>,
                    val speed: Float, val deg: Int, val clouds: Int, val rain: Float)
data class ForecastResult(val city: City, val list: List<Forecast>)

class ForecastRequest(val zipCode: String) {
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val HTTP_HOST = "http://api.openweathermap.org"
        private val URL = "$HTTP_HOST/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="

        fun generateIconUrl(icon: String) = HTTP_HOST + "/img/w/$icon.png"
    }

    fun excecute(): ForecastResult {
        val forecastJsonStr = URL(COMPLETE_URL + zipCode).readText()
        return Gson().fromJson(forecastJsonStr, ForecastResult::class.java)
    }

}