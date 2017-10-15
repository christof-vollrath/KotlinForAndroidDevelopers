package net.taobits.kotlinforandroiddevelopers.app

import android.app.Application
import android.database.sqlite.SQLiteOpenHelper
import net.taobits.kotlinforandroiddevelopers.db.ForecastDbHelper

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    val database: SQLiteOpenHelper by lazy {
        ForecastDbHelper()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database.writableDatabase
    }

}