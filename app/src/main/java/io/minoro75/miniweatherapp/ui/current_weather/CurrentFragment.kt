package io.minoro75.miniweatherapp.ui.current_weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.data.Hourly
import io.minoro75.miniweatherapp.utils.Status
import io.minoro75.miniweatherapp.utils.checkSelfPermissionFragment
import io.minoro75.miniweatherapp.utils.requestPermissionsFragment
import io.minoro75.miniweatherapp.utils.shouldShowRequestPermissionRationaleFragment


const val PERMISSION_REQUEST_LOCATION = 1337

@AndroidEntryPoint
class CurrentFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val currentViewModel: CurrentViewModel by viewModels()

    override fun onRequestPermissionsResult( //response on request permissions
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
        if (checkSelfPermissionFragment(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            //ToDo: IMPLEMENT LOCATION
            Snackbar.make(requireView(), "Location 00/00", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationaleFragment(Manifest.permission.ACCESS_FINE_LOCATION) == true) {
            requestPermissionsFragment(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
        } else {
            requestPermissionsFragment(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
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
        val textView: TextView = root.findViewById(R.id.tv_current_fragment)
        val progressBar: ProgressBar = root.findViewById(R.id.pb_current_fragment)

        val fab: FloatingActionButton = root.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            getDeviceLocation()
        }





        currentViewModel.weather.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    textView.text = it.data!!.current.temp.toString()
                    progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    textView.text = it.message
                }
            }
        })



        return root
    }


}