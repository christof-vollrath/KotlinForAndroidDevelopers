package net.taobits.kotlinforandroiddevelopers.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
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
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val weekForecast = RequestForecastCommand("94043").execute()
            uiThread {
                longToast("Weather data received")
                forecastList.adapter = ForecastListAdapter(weekForecast) { forecast -> toast(forecast.date) }
            }
        }
    }
}

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick: (Forecast) -> Unit) : Adapter<ForeccastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeccastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ForeccastViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ForeccastViewHolder, position: Int) {
        holder.bindForecast(weekForecast.dailyForecast[position])
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size
}

class ForeccastViewHolder(val view: View, val itemClick:  (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bindForecast(forecast: Forecast) {
        with(forecast) {
            Picasso.with(itemView.context).load(iconUrl).into(view.icon)
            view.date.text = date
            view.description.text = description
            view.maxTemperature.text = "${high.toString()}"
            view.minTemperature.text = "${low.toString()}"
            view.setOnClickListener { (itemClick(this)) }
        }
    }
}

