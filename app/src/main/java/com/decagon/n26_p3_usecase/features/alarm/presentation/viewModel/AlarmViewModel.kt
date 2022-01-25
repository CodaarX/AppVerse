package com.decagon.n26_p3_usecase.features.alarm.presentation.viewModel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decagon.n26_p3_usecase.features.alarm.broadcastReceiver.AlarmBroadcastReceiver
import com.decagon.n26_p3_usecase.features.alarm.data.SaveData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(private val saveData : SaveData) : ViewModel() {

    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data

    fun setData(data: String) {
        _data.value = data
    }

    fun saveData(hour: Int, minute: Int) = saveData.SaveData(hour, minute)

    fun getHour() = saveData.getHour()

    fun getMinute() = saveData.getMinute()

}