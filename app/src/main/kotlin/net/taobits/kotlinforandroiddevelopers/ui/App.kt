package net.taobits.kotlinforandroiddevelopers.ui

import android.app.Application
import net.taobits.kotlinforandroiddevelopers.db.ForecastDbHelper

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ForecastDbHelper.instance.writableDatabase
    }

}