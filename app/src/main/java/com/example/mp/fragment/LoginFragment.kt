package com.example.mp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.mp.recyclerviews.R
import com.example.mp.recyclerviews.databinding.FragmentLoginBinding
import com.example.mp.retrofit.NetworkApi
import com.subscribe.snpa.SNPA.models.AuthDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginFragment : BaseFragment() {


    private lateinit var mLoginBinding: FragmentLoginBinding
    private val mAuthDTO: AuthDTO = AuthDTO()
    private val mNetworkApi = NetworkApi.getService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        mLoginBinding.auth = mAuthDTO

        mView = mLoginBinding.root

        mView.findViewById<View>(R.id.login).setOnClickListener {
            doLogin()
        }
        return mView
    }


    private fun doLogin() {

        if (mAuthDTO.isVendor) {
            doVendorLogin()
        } else {
            doCustomerLogin()
        }
    }

    private fun doVendorLogin() {

        showProgress(true)
        mCompositeDisposable.add(
            mNetworkApi.doVendorLogin(mAuthDTO)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    showProgress(false)
                    if (it.statusCode == 200) {
                        Navigation.findNavController(mView).navigate(R.id.action_loginFragment_to_dashboardFragment)
                    }

                }, {
                    showProgress(false)
                    Toast.makeText(context, "Error While logging in!!", Toast.LENGTH_LONG).show()

                })
        )

    }

    private fun doCustomerLogin() {
        showProgress(true)
        mCompositeDisposable.add(
            mNetworkApi.doCustomerLogin(mAuthDTO)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showProgress(false)
                    if (it.statusCode == 200) {
                        Navigation.findNavController(mView).navigate(R.id.action_loginFragment_to_dashboardFragment)
                    }
                }, {
                    showProgress(false)
                    Toast.makeText(context, "Error While logging in!!", Toast.LENGTH_LONG).show()
                })
        )

    }


}
