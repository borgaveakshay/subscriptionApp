package com.example.mp.viewmodels

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.subscribe.snpa.SNPA.models.CustomerDTO
import com.subscribe.snpa.SNPA.models.VendorDTO

class RegistrationViewModel : ViewModel() {

    val mCustomerDTO: CustomerDTO = CustomerDTO()
    val mVendorDTO: VendorDTO = VendorDTO()



}