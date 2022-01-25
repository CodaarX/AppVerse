package com.decagon.n26_p3_usecase.features.alarm.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.core.MainActivity
import com.decagon.n26_p3_usecase.features.alarm.presentation.viewController.AlarmFragment

object NotificationService {

    private const val NOTIFIYTAG="new request"

    fun notify(context: Context, message:String, number:Int){

        val intent= Intent(context, MainActivity::class.java)

        val builder= NotificationCompat.Builder(context)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("New request")
            .setContentText(message)
            .setNumber(number)
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentIntent(PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("default")
        } else {
            builder.setDefaults(Notification.DEFAULT_ALL)
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFIYTAG, 0, builder.build())
    }

}