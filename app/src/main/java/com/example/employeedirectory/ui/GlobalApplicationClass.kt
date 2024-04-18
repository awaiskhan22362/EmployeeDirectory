package com.example.employeedirectory.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class GlobalApplicationClass : Application() {
    //var environment = AdjustConfig.ENVIRONMENT_SANDBOX
   // var environment = AdjustConfig.ENVIRONMENT_PRODUCTION

    public companion object {
        @JvmStatic
        var applicationHandler: Handler? = null
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        var context: Context? = null
    }

    override fun attachBaseContext(basee: Context) {
        super.attachBaseContext(basee)

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        applicationHandler = applicationContext?.getMainLooper()?.let { Handler(it) }



    }
}