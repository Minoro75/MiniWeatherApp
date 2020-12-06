package io.minoro75.miniweatherapp.ui.hourly_weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.data.Hourly
import io.minoro75.miniweatherapp.utils.Status

@AndroidEntryPoint
class HourlyFragment : Fragment() {

    private val hourlyViewModel: HourlyViewModel by viewModels()
    private lateinit var adapter: HourlyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_hourly, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.rv_hourly_fragment)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HourlyAdapter(arrayListOf())
        recyclerView.adapter = adapter
        //val textView: TextView = root.findViewById(R.id.tv_hourly_fragment)

        hourlyViewModel.hourlyForecast.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list -> renderList(list) }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })

        return root

    }

    private fun renderList(weatherList: List<Hourly>) {
        adapter.addData(weatherList)
        adapter.notifyDataSetChanged()
    }
}