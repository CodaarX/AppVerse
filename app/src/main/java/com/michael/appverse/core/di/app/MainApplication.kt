package com.michael.appverse.core.di.app

import android.app.Application
import com.michael.appverse.commons.utils.NetworkLiveData
import com.example.mike_utils.MikeNetworkLiveData
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MikeNetworkLiveData.init(this)
        NetworkLiveData.init(this)
        Timber.plant(Timber.DebugTree())
    }
}