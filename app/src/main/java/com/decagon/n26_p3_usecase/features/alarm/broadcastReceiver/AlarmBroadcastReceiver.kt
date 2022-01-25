package com.decagon.n26_p3_usecase.features.alarm.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.decagon.n26_p3_usecase.features.alarm.service.NotificationService
import javax.inject.Inject

class AlarmBroadcastReceiver @Inject constructor() : BroadcastReceiver() {

    // receive the broadcast
    override fun onReceive(context: Context?, intent: Intent?) {

        // if intent has the action
        if (intent!!.action.equals("usecase.ACTION_ALARM")) {
            val intentData = intent.extras
            // send the notification
            NotificationService.notify(context!!, intentData!!.getString("message")!!,  10)
        }
    }

}