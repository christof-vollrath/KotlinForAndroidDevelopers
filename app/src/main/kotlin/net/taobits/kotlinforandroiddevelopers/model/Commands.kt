package net.taobits.kotlinforandroiddevelopers.model

import net.taobits.kotlinforandroiddevelopers.datasource.ForecastProvider
import net.taobits.kotlinforandroiddevelopers.weatherclient.ApiDataMapper
import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastRequest

abstract class Command<out T> {
    abstract fun execute(): T
} 

class RequestForecastCommand(val zipCode: Long, val forecastProvider: ForecastProvider = ForecastProvider()): Command<ForecastList>() {
    companion object {
        val DAYS = 6
        // Not 7 days like in the book, because of the time delay between europe and california,
        // the returned weather forecast might start from yesterday
        // and therefore only contain 6 days from today on
    }
    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}
