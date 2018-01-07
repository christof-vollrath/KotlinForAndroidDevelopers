package net.taobits.kotlinforandroiddevelopers.weatherclient

import net.taobits.kotlinforandroiddevelopers.model.Forecast
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import java.util.*
import java.util.concurrent.TimeUnit
import net.taobits.kotlinforandroiddevelopers.weatherclient.Forecast as ApiForecast
import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastResult as ApiForecastResult

object ApiDataMapper {

    fun convert(zipCode: Long, forecast: ApiForecastResult): ForecastList =
            ForecastList(zipCode, forecast.city.name, forecast.city.country, convertForecastList(forecast.list))

    fun convertForecastList(list: List<ApiForecast>): List<Forecast> =
        list.mapIndexed { i, forecast ->
            val dt = (Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())) / 1000
            convertForecast(forecast.copy(dt = dt))
        }

    fun convertForecast(forecast: ApiForecast): Forecast =
            Forecast(0, forecast.dt, forecast.weather[0].description,
                    forecast.temp.max.toInt(), forecast.temp.min.toInt(), ForecastRequest.generateIconUrl(forecast.weather[0].icon))
}