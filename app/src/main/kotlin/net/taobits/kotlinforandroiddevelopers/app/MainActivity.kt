package net.taobits.kotlinforandroiddevelopers.app

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import net.taobits.kotlinforandroiddevelopers.R
import net.taobits.kotlinforandroiddevelopers.model.ForecastList
import net.taobits.kotlinforandroiddevelopers.model.RequestForecastCommand
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(javaClass.simpleName, "Init layout")
        forecastList.layoutManager = LinearLayoutManager(this)

        Log.d(javaClass.simpleName, "starting async")
        doAsync {
            val weekForecast = RequestForecastCommand("94043").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(weekForecast)
                longToast("Request performed") }
        }
    }
}

class ForecastListAdapter(val weekForecast: ForecastList) : Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (weekForecast.dailyForecast[position]) {
            holder.textView.text = "$date - $description - $high/$low"
        }
    }

    override fun getItemCount(): Int = weekForecast.dailyForecast.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}
