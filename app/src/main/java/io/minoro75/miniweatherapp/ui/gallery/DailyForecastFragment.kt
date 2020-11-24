package io.minoro75.miniweatherapp.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.minoro75.miniweatherapp.R

class DailyForecastFragment : Fragment() {

    private lateinit var dailyForecastViewModel: DailyForecastViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dailyForecastViewModel =
                ViewModelProvider(this).get(DailyForecastViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_daily_forecast, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        dailyForecastViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}