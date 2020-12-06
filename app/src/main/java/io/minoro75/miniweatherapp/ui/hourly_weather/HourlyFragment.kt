package io.minoro75.miniweatherapp.ui.hourly_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.utils.Status

@AndroidEntryPoint
class HourlyFragment : Fragment() {

    private val hourlyViewModel: HourlyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_hourly, container, false)
        val textView: TextView = root.findViewById(R.id.tv_hourly_fragment)

        hourlyViewModel.hourlyForecast.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    textView.text = it.data!!.size.toString()
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    textView.text = it.message
                }
            }
        })

        return root

    }
}