package com.decagon.n26_p3_usecase.app

import android.app.Application
import com.decagon.n26_p3_usecase.commons.utils.NetworkLiveData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkLiveData.init(this)
    }
}