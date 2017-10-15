package net.taobits.kotlinforandroiddevelopers.model

import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastRequest

abstract class Command<out T> {
    abstract fun execute(): T
} 

class RequestForecastCommand(val zipCode: String): Command<ForecastList>() {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        val forecastResult = forecastRequest.execute()
        return ApiDataMapper.convert(forecastResult)
    }
}
