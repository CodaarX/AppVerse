package com.decagon.n26_p3_usecase.features.locationTracker.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.timber
import com.decagon.n26_p3_usecase.features.locationTracker.utils.TrackingUtils
import com.decagon.n26_p3_usecase.features.locationTracker.utils.TrackingUtils.NOTIFICATION_ID
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


typealias PolyLine = MutableList<LatLng>
typealias PolyLines = MutableList<PolyLine>

@AndroidEntryPoint
class TrackingService : LifecycleService() {

    private var isFirstRun = true
    private var serviceKilled = false

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    private lateinit var currentNotificationBuilder: NotificationCompat.Builder

    private val timeRunInSeconds = MutableLiveData<Long>()

    // perform operations from outside the service
    companion object {
        val timeRunInMillis = MutableLiveData<Long>()
        var isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<PolyLines>()
        var userCurrentLocation = MutableLiveData<LatLng>()
    }

    private fun postInitialValue(){
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
        timeRunInSeconds.postValue(0L)
        timeRunInMillis.postValue(0L)
    }

    override fun onCreate()  {
        super.onCreate()
        currentNotificationBuilder = baseNotificationBuilder
        postInitialValue()
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        isTracking.observe(this) {
            updateLocationTracking(it)
            updateNotificationTrackingState(it)
        }
    }

    private fun addEmptyPolyLine() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    private val locationCallback = object : LocationCallback(){ //callback for location updates

        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result) // get location from callback

            val mLastLocation: Location = result.lastLocation

            val nowLocation = LatLng(
                mLastLocation.latitude,
                mLastLocation.longitude
            )

            userCurrentLocation.postValue(nowLocation)

            if(isTracking.value!!){
                result.locations.let { locations ->
                    for (location in locations){ // loop through locations
                        addPoints(location)
                        timber("LOCATION ${location.latitude} - ${location.longitude}")
                    }
                }
            }

        }
    }

    private fun updateNotificationTrackingState(isTracking: Boolean){
        val notificationActionText = if(isTracking) "Pause" else "Resume"
        val pendingIntent = if(isTracking){
            val pauseIntent = Intent(this, TrackingService::class.java).apply {
                action = TrackingUtils.ACTION_PAUSE_SERVICE
            }
            PendingIntent.getService(this, 1, pauseIntent, FLAG_UPDATE_CURRENT)
        } else {
            val resumeIntent = Intent(this, TrackingService::class.java).apply {
                action = TrackingUtils.ACTION_START_OR_RESUME_SERVICE
            }
            PendingIntent.getService(this, 1, resumeIntent, FLAG_UPDATE_CURRENT)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        currentNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(currentNotificationBuilder, ArrayList<NotificationCompat.Action>())
        }

        if (!serviceKilled){
            currentNotificationBuilder = baseNotificationBuilder.apply {
                addAction(R.drawable.ic_pause_black_24dp, notificationActionText, pendingIntent)
            }
            notificationManager.notify(NOTIFICATION_ID, currentNotificationBuilder.build())
        }

        timeRunInSeconds.observe(this) {
            if (!serviceKilled){
                val notification = currentNotificationBuilder.apply {
                    setContentText(TrackingUtils.getFormattedStopWatchTime(it * 1000L, false))
                }.build()
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun updateLocationTracking(isTracking: Boolean){
        if(isTracking){
            if(TrackingUtils.hasLocationPermission(this)) {
                val request = com.google.android.gms.location.LocationRequest().apply {
                    interval = TrackingUtils.LOCATION_UPDATE_INTERVAL // location update will be triggered approximately every specified interval
                    fastestInterval = TrackingUtils.FASTEST_LOCATION_UPDATE_INTERVAL // location updates will be received faster than this interval
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(request, locationCallback,  Looper.getMainLooper()) // request location updates
            } else {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback) // remove location
            }
        }
    }

    private fun addPoints(location: Location){
        location.let {
            val position = LatLng(location.latitude, location.longitude)
            pathPoints.value?.apply {
                last().add(position)
                pathPoints.postValue(this)
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        intent.let {
            when(it.action) {
                TrackingUtils.ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun){
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        timber("Resuming service...")
                        startTimer()
                    }
                }
                TrackingUtils.ACTION_PAUSE_SERVICE -> {
                    pauseService()
                }
                TrackingUtils.ACTION_STOP_SERVICE -> {
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private var isTimerEnabled = false
    private var lapTime = 0L // for each lap
    private var timeRun = 0L // total time run
    private var timeStarted = 0L // time started
    private var lastSecondTimeStamp = 0L // time stamp of last second

    private fun startTimer(){
        addEmptyPolyLine()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!){
                lapTime = System.currentTimeMillis() - timeStarted // time elapsed since start
                timeRunInMillis.postValue(timeRun + lapTime) // post to UI
                if(timeRunInMillis.value!! >= lastSecondTimeStamp + 1000L){ // post to UI every second
                    timeRunInSeconds.postValue(timeRunInSeconds.value!! + 1)  // post to UI
                    lastSecondTimeStamp += 1000L // update last second time stamp
                }
                delay(TrackingUtils.TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }

    private fun pauseService(){
        isTracking.postValue(false)
        isTimerEnabled = false
    }

    private fun startForegroundService() {

        startTimer()
        isTracking.postValue(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { createNotificationChannel(notificationManager) }
            startForeground(NOTIFICATION_ID, baseNotificationBuilder.build()) // start the service in foreground
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){ // to create a notification channel
        val channel = NotificationChannel(
            TrackingUtils.NOTIFICATION_CHANNEL_ID,
            TrackingUtils.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun killService(){
        serviceKilled = true
        isFirstRun = true
        pauseService()
        postInitialValue()
        stopForeground(true)
        stopSelf()
    }
}