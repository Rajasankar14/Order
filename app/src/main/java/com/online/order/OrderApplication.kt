package com.online.order

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OrderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}