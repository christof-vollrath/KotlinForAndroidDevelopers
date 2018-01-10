package net.taobits.kotlinforandroiddevelopers.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import net.taobits.kotlinforandroiddevelopers.R
import net.taobits.kotlinforandroiddevelopers.model.Forecast
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import net.taobits.kotlinforandroiddevelopers.model.RequestForecastCommand
import org.jetbrains.anko.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar: Toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    val zipCode: Long by LongPreference(this, SettingsActivity.ZIP_CODE, SettingsActivity.DEFAULT_ZIP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)

        loadForecast()
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    fun loadForecast() {
        doAsync {
            val weekForecast = RequestForecastCommand(zipCode).execute()
            uiThread {
                longToast("Weather data received")
                forecastList.adapter = ForecastListAdapter(weekForecast) {
                    startActivity<DetailActivity>(DetailActivity.ID to it.id, DetailActivity.CITY_NAME to weekForecast.city)
                }
                toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
            }
        }
    }
}

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: (Forecast) -> Unit) : Adapter<ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bindForecast(weekForecast.dailyForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size
}

class ForecastViewHolder(val view: View, val itemClick:  (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bindForecast(forecast: Forecast) {
        with(forecast) {
            Picasso.with(itemView.context).load(iconUrl).into(view.icon)
            view.date.text = convertDate(date)
            view.description.text = description
            view.maxTemperature.text = "$high"
            view.minTemperature.text = "$low"
            view.setOnClickListener { (itemClick(this)) }
        }
    }
}


fun convertDate(date: Long): String {
    val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US)
    return df.format(date * 1000)
}

