package com.example.mp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mp.models.LocationDTO
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class LocationUpdateManager(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)


    @SuppressLint("MissingPermission")
    fun getLastLocation(): LiveData<LocationDTO> {

        val locationLiveData: MutableLiveData<LocationDTO> = MutableLiveData()

        fusedLocationClient.lastLocation.addOnSuccessListener {

            it?.let { location ->


                val geoCoder = Geocoder(context, Locale.getDefault())
                val address: List<Address> = geoCoder.getFromLocation(location.latitude, location.longitude, 1)

                address.let { list ->

                    locationLiveData.postValue(
                        LocationDTO(
                            lat = location.latitude,
                            long = location.longitude,
                            address = list[0].getAddressLine(0)
                        )
                    )

                }


            }

        }

        return locationLiveData
    }
}