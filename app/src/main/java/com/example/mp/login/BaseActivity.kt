package com.example.mp.login

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mp.utils.LocationUpdateManager
import kotlinx.android.synthetic.main.activity_login.*

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {


    protected lateinit var mLocationUpdateManager: LocationUpdateManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLocationUpdateManager = LocationUpdateManager(this)
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
            ContextCompat.checkSelfPermission(this, it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            ActivityCompat.requestPermissions(this, perm, permissionId)
        }

        return havePermissions
    }
}