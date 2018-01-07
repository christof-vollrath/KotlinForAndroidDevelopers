package net.taobits.kotlinforandroiddevelopers.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.squareup.picasso.Picasso
import net.taobits.kotlinforandroiddevelopers.R
import net.taobits.kotlinforandroiddevelopers.model.Forecast
import net.taobits.kotlinforandroiddevelopers.model.RequestDayForecastCommand
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat
import java.util.*

class DetailActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailAcitivity:cityName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread { bindForecast(result)
            }
        }
    }

    fun bindForecast(forecast: Forecast) = with (forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = it.first.toString()
        it.second.textColor = color(
                when (it.first) {
                    in -50..0 -> android.R.color.holo_red_dark
                    in 0..15 -> android.R.color.holo_orange_dark
                    else -> android.R.color.holo_green_dark
                }
        )
    }
}

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String =
        DateFormat.getDateInstance(dateFormat, Locale.getDefault()).format(this)

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)