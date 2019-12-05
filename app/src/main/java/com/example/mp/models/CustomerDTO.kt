package com.subscribe.snpa.SNPA.models

import androidx.databinding.BaseObservable

data class CustomerDTO(
    var name: String = "",
    var customerId: Int = 0,
    var email: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var address: String = "",
    var areaDTO: AreaDTO = AreaDTO(0, "")
) : BaseObservable()