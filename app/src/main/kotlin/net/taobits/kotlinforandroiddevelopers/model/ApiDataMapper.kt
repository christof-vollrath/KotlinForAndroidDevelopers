package net.taobits.kotlinforandroiddevelopers.model

import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastRequest
import java.text.DateFormat
import java.util.*
import net.taobits.kotlinforandroiddevelopers.weatherclient.Forecast as ApiForecast
import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastResult as ApiForecastResult

object ApiDataMapper {

    fun convert(forecast: ApiForecastResult): ForecastList =
        ForecastList(forecast.city.name, forecast.city.country, convertForecastList(forecast.list))

    fun convertForecastList(list: List<ApiForecast>): List<Forecast> =
        list.map { convertForecast(it) }

    fun convertForecast(forecast: ApiForecast): Forecast =
        Forecast(convertDate(forecast.dt), forecast.weather[0].description,
            forecast.temp.max.toInt(), forecast.temp.min.toInt(), ForecastRequest.generateIconUrl(forecast.weather[0].icon))

    fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US)
        return df.format(date * 1000)
    }
}