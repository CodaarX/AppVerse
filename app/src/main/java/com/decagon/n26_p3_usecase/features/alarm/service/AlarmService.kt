package com.decagon.n26_p3_usecase.features.alarm.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.decagon.n26_p3_usecase.features.alarm.broadcastReceiver.AlarmBroadcastReceiver
import com.decagon.n26_p3_usecase.features.alarm.presentation.viewModel.AlarmViewModel
import java.util.*

object AlarmService {

    fun setAlarm(context: Context, viewModel: AlarmViewModel) {

        val hour:Int= viewModel.getHour()
        val minute:Int= viewModel.getMinute()
        val calender= Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY,hour)
        calender.set(Calendar.MINUTE,minute)
        calender.set(Calendar.SECOND,0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent= Intent(context, AlarmBroadcastReceiver::class.java) // create intent to send to the broadcast receiver
        intent.putExtra("message","alarm time")
        intent.action="usecase.ACTION_ALARM"

        val pi= PendingIntent.getBroadcast(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, calender.timeInMillis, AlarmManager.INTERVAL_DAY, pi )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)
        alarmManager.cancel(pendingIntent)
    }
}