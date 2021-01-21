package io.minoro75.miniweatherapp.ui.current_weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.utils.Status
import io.minoro75.miniweatherapp.utils.requestPermissionsFragment
import io.minoro75.miniweatherapp.utils.shouldShowRequestPermissionRationaleFragment


const val PERMISSION_REQUEST_LOCATION = 1337

@AndroidEntryPoint
class CurrentFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val currentViewModel: CurrentViewModel by viewModels()
    lateinit var locationManager: LocationManager
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    override fun onRequestPermissionsResult( //response on request permissions
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.size == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation()
                Snackbar.make(requireView(), "Permission granted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                Snackbar.make(requireView(), "Permission not granted", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            }
        }
    }

    private fun getDeviceLocation() { //check if permission granted already
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            //get location
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 0F
            ) { location ->
                lat = location.latitude
                lon = location.longitude
            }

            Snackbar.make(requireView(), "Location lat:${lat} lon:${lon} ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            currentViewModel.getWeatherInLocation(lat, lon)
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationaleFragment(Manifest.permission.ACCESS_FINE_LOCATION) == true) {
            requestPermissionsFragment(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_LOCATION
            )
        } else {
            requestPermissionsFragment(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_LOCATION
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_current, container, false)
        val progressIndicator =
            root.findViewById<CircularProgressIndicator>(R.id.pi_current_fragment)

        val temp = root.findViewById<TextView>(R.id.tv_temp)
        val feelsLike = root.findViewById<TextView>(R.id.tv_feels_like)
        val humidity = root.findViewById<TextView>(R.id.tv_humidity)
        val pressure = root.findViewById<TextView>(R.id.tv_pressure)
        val imageCloudness = root.findViewById<ImageView>(R.id.iv_cloudness)

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            getDeviceLocation()
        }
        //observing livedata from viewmodel
        currentViewModel.weather.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressIndicator.visibility = View.GONE
                    root.findViewById<CardView>(R.id.cv_current_weather).visibility = View.VISIBLE
                    //ToDo: add databinding for Weather obj
                    temp.text = it.data!!.current.temp.toString()
                    feelsLike.text = it.data.current.feels_like.toString()
                    humidity.text = it.data.current.humidity.toString()
                    pressure.text = it.data.current.pressure.toString()
                    Glide.with(this) // loading icon to imageview
                        .load("https://openweathermap.org/img/wn/${it.data.current.weather[0].icon}@2x.png")
                        .centerCrop()
                        .into(imageCloudness)
                }
                Status.LOADING -> {
                    root.findViewById<CardView>(R.id.cv_current_weather).visibility = View.GONE
                    progressIndicator.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressIndicator.visibility = View.GONE
                    root.findViewById<CardView>(R.id.cv_current_weather).visibility = View.GONE
                }
            }
        })



        return root
    }


}