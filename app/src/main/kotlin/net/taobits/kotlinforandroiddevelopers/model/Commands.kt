package net.taobits.kotlinforandroiddevelopers.model

import net.taobits.kotlinforandroiddevelopers.datasource.ForecastProvider

abstract class Command<out T> {
    abstract fun execute(): T
} 

class RequestForecastCommand(val zipCode: Long, val forecastProvider: ForecastProvider = ForecastProvider()): Command<ForecastList>() {
    companion object {
        val DAYS = 6
    }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}

class RequestDayForecastCommand(val id: Long, val forecastProvider: ForecastProvider = ForecastProvider()): Command<Forecast>() {
    override fun execute(): Forecast = forecastProvider.requestDayForecast(id)
}
