package io.minoro75.miniweatherapp.ui.current_weather

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.minoro75.miniweatherapp.R
import io.minoro75.miniweatherapp.utils.Status
import io.minoro75.miniweatherapp.utils.requestPermissionsFragment

const val PERMISSION_REQUEST_INTERNET = 1212

@AndroidEntryPoint
class CurrentFragment : Fragment() {

    private val currentViewModel: CurrentViewModel by viewModels<CurrentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_current, container, false)
        val textView: TextView = root.findViewById(R.id.tv_current_fragment)
        val progressBar: ProgressBar = root.findViewById(R.id.pb_current_fragment)
        requestPermissions()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun requestPermissions() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
            //Snackbar.make(requireView(),"neeed",Snackbar.LENGTH_INDEFINITE)
            requestPermissionsFragment(
                arrayOf(Manifest.permission.INTERNET),
                PERMISSION_REQUEST_INTERNET
            )
        } else {
            //Snackbar.make(requireView(),"please approve", Snackbar.LENGTH_SHORT)
            requestPermissionsFragment(
                arrayOf(Manifest.permission.INTERNET),
                PERMISSION_REQUEST_INTERNET
            )
        }
    }
}