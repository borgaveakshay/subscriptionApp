package com.example.mp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.mp.models.LocationDTO
import com.example.mp.utils.Constants

abstract class LocationFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkLocation()
    }

    private fun checkLocation() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (checkPermissionFlag(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    Constants.LOCATION_PERMISSION
                )
            ) getLocation()
        } else
            getLocation()

    }

    private fun getLocation() {

        mLocationUpdateManager.getLastLocation().observe(this, Observer {

            it?.let {
                locationAvailable(it)
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == Constants.LOCATION_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()

            } else {
                Toast.makeText(context, "Location permission not granted!!", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    abstract fun locationAvailable(locationDTO: LocationDTO)
}