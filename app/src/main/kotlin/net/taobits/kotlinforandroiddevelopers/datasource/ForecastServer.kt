package net.taobits.kotlinforandroiddevelopers.datasource

import android.util.Log
import net.taobits.kotlinforandroiddevelopers.db.ForecastDb
import net.taobits.kotlinforandroiddevelopers.model.Forecast
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import net.taobits.kotlinforandroiddevelopers.weatherclient.ApiDataMapper
import net.taobits.kotlinforandroiddevelopers.weatherclient.ForecastRequest

class ForecastServer(val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastRequest(zipCode).execute()
        val converted = ApiDataMapper.convert(zipCode, result)
        Log.i("ForecastServer", "Converted from server: $converted" )
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
    override fun requestDayForecast(id: Long): Forecast? = throw UnsupportedOperationException()
}