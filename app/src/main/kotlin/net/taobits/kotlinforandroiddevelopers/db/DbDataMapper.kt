package net.taobits.kotlinforandroiddevelopers.db

import net.taobits.kotlinforandroiddevelopers.model.Forecast
import net.taobits.kotlinforandroiddevelopers.model.ForecastList

class DbDataMapper {
    companion object {
        val instance by lazy { DbDataMapper() }
    }

    fun convertToDomain(cityForecast: CityForecast) = with(cityForecast) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, city, country, daily)
    }

    private fun convertDayToDomain(dayForecast: DayForecast) = with(dayForecast) {
        Forecast(date, description, high, low, iconUrl)
    }

    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val daily = dailyForecast.map { convertDayFromDomain(cityId, it) }
        CityForecast(cityId, city, country, daily)
    }

    private fun convertDayFromDomain(cityId: Long, forecast: Forecast)= with(forecast) {
        DayForecast(date, description, high, low, iconUrl, cityId)
    }
}