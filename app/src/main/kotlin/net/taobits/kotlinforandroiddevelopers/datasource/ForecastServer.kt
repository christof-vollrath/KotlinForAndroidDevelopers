package net.taobits.kotlinforandroiddevelopers.datasource

import net.taobits.kotlinforandroiddevelopers.db.ForecastDb
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import net.taobits.kotlinforandroiddevelopers.weatherclient.ApiDataMapper
import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastRequest

class ForecastServer(val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastRequest(zipCode).execute()
        val converted = ApiDataMapper.convert(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}