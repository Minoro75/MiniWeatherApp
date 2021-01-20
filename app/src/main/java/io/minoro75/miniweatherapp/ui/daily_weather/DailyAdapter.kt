package io.minoro75.miniweatherapp.ui.daily_weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.data.Daily

class DailyAdapter(private val weatherList: ArrayList<Daily>) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dailyWeather: Daily) {
            itemView.findViewById<TextView>(R.id.tv_daily_temp).text =
                dailyWeather.temp.max.toString()

            itemView.findViewById<TextView>(R.id.tv_daily_feels_like_temp).text =
                dailyWeather.feels_like.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder =
        DailyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_daily_item, parent, false)
        )

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) =
        holder.bind(weatherList[position])

    override fun getItemCount(): Int = weatherList.size

    fun addData(list: List<Daily>) {
        weatherList.addAll(list)
    }
}