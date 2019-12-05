package com.example.mp.fragment

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mp.utils.LocationUpdateManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*

open class BaseFragment : Fragment() {

    protected lateinit var mLocationUpdateManager: LocationUpdateManager

    protected lateinit var mCompositeDisposable: CompositeDisposable

    protected lateinit var mView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCompositeDisposable = CompositeDisposable()
        context?.let {
            mLocationUpdateManager = LocationUpdateManager(it)
        }

    }

    protected fun showProgress(show: Boolean) {

        if (progressBar != null) {
            if (show)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.INVISIBLE
        }
    }

    protected fun checkPermissionFlag(perm: Array<String>, permissionId: Int): Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(activity!!.applicationContext, it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            ActivityCompat.requestPermissions(activity as Activity, perm, permissionId)
        }

        return havePermissions
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}