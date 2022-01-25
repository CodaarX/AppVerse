package com.decagon.n26_p3_usecase.features.alarm.data

import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import javax.inject.Inject


class SaveData @Inject constructor(private val sharedPreference: SharedPreference) {

    fun SaveData(hour:Int, minute:Int){
        sharedPreference.saveToSharedPref("ALARM_HOUR", hour)
        sharedPreference.saveToSharedPref("ALARM_MINUTE", minute)
    }

    fun  getHour():Int{
        return sharedPreference.loadFromSharedPref("Int", "ALARM_HOUR")
    }

    fun  getMinute():Int{
        return sharedPreference.loadFromSharedPref("Int", "ALARM_MINUTE")
    }
}