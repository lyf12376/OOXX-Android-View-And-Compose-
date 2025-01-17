package com.example.battleship

import android.app.Application
import com.example.battleship.utils.AppUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this)
    }
}
