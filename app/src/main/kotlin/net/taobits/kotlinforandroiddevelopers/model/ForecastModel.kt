package net.taobits.kotlinforandroiddevelopers.model

data class Forecast(val date: Long, val description: String, val high: Int, val low: Int, val iconUrl: String)

data class ForecastList(val cityId: Long, val city: String, val country: String, val dailyForecast: List<Forecast>) {
    fun size() = dailyForecast.size
}
