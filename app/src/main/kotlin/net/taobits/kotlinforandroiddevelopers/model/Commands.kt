package net.taobits.kotlinforandroiddevelopers.model

abstract class Command<T> {
    abstract fun execute(): T
} 

class RequestForecastCommand(val zipCode: String): Command<ForecastList>() {
    override fun execute(): ForecastList {
        return ForecastList("Berlin", "Germany", forecasts)
    }
}


private val forecasts = listOf(
        Forecast("Mon 6/23", "Sunny", 31, 17),
        Forecast("Tue 6 , 24", "Foggy", 21 , 8),
        Forecast("Wed 6 , 25", "Cloudy", 22 , 17),
        Forecast("Thurs 6 , 26", "Rainy", 18 , 11),
        Forecast("Fri 6 , 27", "Foggy", 21 , 10),
        Forecast("Sat 6 , 28", "TRAPPED IN WEATHERSTATION", 23 , 17),
        Forecast("Sun 6 , 29", "Sunny", 20 , 7)
)