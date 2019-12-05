package com.subscribe.snpa.SNPA.models

import androidx.databinding.BaseObservable

data class AuthDTO(
    var email: String= "",
    var isVendor: Boolean = false
) : BaseObservable()