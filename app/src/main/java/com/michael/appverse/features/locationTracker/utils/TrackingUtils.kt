package com.michael.appverse.features.locationTracker.utils

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.location.Location
import java.util.concurrent.TimeUnit
import android.os.Build
import androidx.annotation.RequiresApi
import com.michael.appverse.features.locationTracker.services.PolyLine
import pub.devrel.easypermissions.EasyPermissions

object TrackingUtils {

    const val LOCATION_PERMISSION_CODE = 10
    const val LOCATION_PERMISSION_REQUEST_CODE = 3

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_UPDATE_INTERVAL = 2000L

    const val  POLY_LINE_COLOR = Color.RED
    const val  POLY_LINE_WIDTH = 8f

    const val MAP_ZOOM = 15f

    const val REQUEST_CHECK_SETTINGS = 100

    const val TIMER_UPDATE_INTERVAL = 50L


    @RequiresApi(Build.VERSION_CODES.Q)
    fun hasLocationPermission(context: Context) =
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
           EasyPermissions.hasPermissions(context,
               Manifest.permission.ACCESS_FINE_LOCATION,
               Manifest.permission.ACCESS_COARSE_LOCATION
           )
        } else  {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    fun calculatePolylineLength(polyLine: PolyLine): Float{
        var distance = 0f
        for(i in 0 until polyLine.size - 2){
            val pos1 = polyLine[i]
            val pos2 = polyLine[i+1]

            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )

            distance += result[0]
        }
        return distance
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if(!includeMillis) {
            return "${if(hours < 10) "0" else ""}$hours:" +
                    "${if(minutes < 10) "0" else ""}$minutes:" +
                    "${if(seconds < 10) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10
        return "${if(hours < 10) "0" else ""}$hours:" +
                "${if(minutes < 10) "0" else ""}$minutes:" +
                "${if(seconds < 10) "0" else ""}$seconds:" +
                "${if(milliseconds < 10) "0" else ""}$milliseconds"

    }

}
