package net.taobits.kotlinforandroiddevelopers

import android.util.Log
import java.net.URL

class WeatherRequest() {

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }

    fun run() {
        val url = COMPLETE_URL + 10001 // zip code of New York
        println("Read text from $url")
        val forecastJsonStr = URL(url).readText()
        println(forecastJsonStr)
    }
}