package com.michael.appverse.features.locationTracker.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.michael.appverse.R
import com.michael.appverse.features.locationTracker.presentation.viewController.TrackLocationActivity
import com.michael.appverse.features.locationTracker.utils.TrackingUtils
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class) // Service scope. each module have its own scope in dagger hilt. { Activity scope, Application, Service }
object ServiceModule {

//    @ActivityScoped //live for the lifetime of the activity
    @ServiceScoped // live for the lifetime of the service  (not the activity)
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext app: Context) = FusedLocationProviderClient(app)

    @ServiceScoped // live for the lifetime of the service  (not the activity)
    @Provides
    fun provideTrackLocationActivityPendingIntent(@ApplicationContext app: Context): PendingIntent = PendingIntent.getActivity(
        app,
        0,
        Intent(
            app,
            TrackLocationActivity::class.java).also {
            it.action = TrackingUtils.ACTION_SHOW_TRACKING_FRAGMENT
        }, PendingIntent.FLAG_UPDATE_CURRENT)


    @ServiceScoped // live for the lifetime of the service  (not the activity)
    @Provides
    fun provideBaseNotificaitionBuilder(@ApplicationContext app: Context, pendingIntent: PendingIntent) =
        NotificationCompat.Builder(app, TrackingUtils.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_run)
        .setContentTitle("Tracking")
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent) // to open the activity when the notification is clicked (pending intent)
}