package com.example.hiltexample

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(ApplicationClass::class.simpleName, "onCreate: ")
    }

}