package net.taobits.kotlinforandroiddevelopers.model

data class Forecast(val date: String, val description: String, val high: Int, val low: Int)

data class ForecastList(val city: String, val country: String, val dailyForecast: List<Forecast>)
