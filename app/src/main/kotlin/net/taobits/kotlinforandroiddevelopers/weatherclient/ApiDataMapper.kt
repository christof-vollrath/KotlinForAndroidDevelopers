package net.taobits.kotlinforandroiddevelopers.weatherclient

import net.taobits.kotlinforandroiddevelopers.model.Forecast
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import net.taobits.kotlinforandroiddevelopers.weatherclient.Forecast as ApiForecast
import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastResult as ApiForecastResult

object ApiDataMapper {

    fun convert(zipCode: Long, forecast: ApiForecastResult): ForecastList =
            ForecastList(zipCode, forecast.city.name, forecast.city.country, convertForecastList(forecast.list))

    fun convertForecastList(list: List<ApiForecast>): List<Forecast> =
        list.map { convertForecast(it) }

    fun convertForecast(forecast: ApiForecast): Forecast =
            Forecast(0, forecast.dt, forecast.weather[0].description,
                    forecast.temp.max.toInt(), forecast.temp.min.toInt(), ForecastRequest.generateIconUrl(forecast.weather[0].icon))
}