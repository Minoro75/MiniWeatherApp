package io.minoro75.miniweatherapp.ui.current_weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.miniweatherapp.databinding.FragmentCurrentBinding
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

    private var _binding: FragmentCurrentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

            /*Snackbar.make(requireView(), "Location lat:${lat} lon:${lon} ", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            if (lat != 0.0 && lon != 0.0) {
                currentViewModel.getWeatherInLocation(lat, lon)
            }
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
        _binding = FragmentCurrentBinding.inflate(inflater, container, false)
        val view = binding.root

        //get location button
        binding.fab.setOnClickListener {
            getDeviceLocation()
        }

        binding.btSendData.setOnClickListener {
            if (binding.etLat.text.isNotEmpty() &&
                binding.etLon.text.isNotEmpty()
            ) {

                lat = binding.etLat.text.toString().toDouble()
                lon = binding.etLon.text.toString().toDouble()
                currentViewModel.getWeatherInLocation(lat, lon)
            } else {
                Snackbar.make(requireView(), "Writedown correct dataset", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
        }

        //observing livedata from viewmodel
        currentViewModel.weather.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.piCurrentFragment.visibility = View.GONE
                    binding.cvCurrentWeather.visibility = View.VISIBLE
                    //ToDo: add databinding for Weather obj
                    binding.tvTemp.text = it.data!!.current.temp.toString()
                    binding.tvFeelsLike.text = it.data.current.feels_like.toString()
                    binding.tvHumidity.text = it.data.current.humidity.toString()
                    binding.tvPressure.text = it.data.current.pressure.toString()
                    Glide.with(this) // loading icon to imageview
                        .load("https://openweathermap.org/img/wn/${it.data.current.weather[0].icon}@2x.png")
                        .centerCrop()
                        .into(binding.ivCloudness)
                }
                Status.LOADING -> {
                    binding.cvCurrentWeather.visibility = View.GONE
                    binding.piCurrentFragment.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.piCurrentFragment.visibility = View.GONE
                    binding.cvCurrentWeather.visibility = View.GONE
                }
            }
        })



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}