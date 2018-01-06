package net.taobits.kotlinforandroiddevelopers.datasource

import net.taobits.kotlinforandroiddevelopers.db.ForecastDb
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import java.util.*

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
}

class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {
    companion object {
        val DAY_IN_SECONDS = 60 * 60 * 24
        val DAY_IN_MILLIS = 1000 * DAY_IN_SECONDS
        val SOURCES = listOf(ForecastDb(), ForecastServer())

    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = sources.firstResult { requestSource(it, days, zipCode) }

    fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
        val res = source.requestForecastByZipCode(zipCode, todayTimeSpan())
        return if (res != null && res.size() >= days) res else null
    }

    fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_SECONDS // rounded to day
}

inline fun <T, R: Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No element matching predicate was found")
}