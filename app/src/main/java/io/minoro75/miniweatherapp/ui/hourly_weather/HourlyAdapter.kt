package io.minoro75.miniweatherapp.ui.hourly_weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.data.Hourly

class HourlyAdapter(
    private val weatherList: ArrayList<Hourly>
) : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hourlyWeather: Hourly) {
            itemView.findViewById<TextView>(R.id.tv_hourly_temp).text =
                hourlyWeather.temp.toString()
            itemView.findViewById<TextView>(R.id.tv_feels_like_temp).text =
                hourlyWeather.feels_like.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder =
        HourlyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_hourly_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) =
        holder.bind(weatherList[position])

    override fun getItemCount(): Int = weatherList.size

    fun addData(list: List<Hourly>) {
        weatherList.addAll(list)
    }
}