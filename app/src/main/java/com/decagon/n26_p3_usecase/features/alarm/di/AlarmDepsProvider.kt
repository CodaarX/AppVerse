package com.decagon.n26_p3_usecase.features.alarm.di

import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import com.decagon.n26_p3_usecase.features.alarm.data.SaveData
import com.decagon.n26_p3_usecase.features.alarm.presentation.viewModel.AlarmViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AlarmDepsProvider {

    @Provides
    @Singleton
    fun provideAlarmViewModel(saveData : SaveData) : AlarmViewModel = AlarmViewModel(saveData)

    @Provides
    @Singleton
    fun provideSaveData(sharedPreference: SharedPreference) : SaveData  = SaveData(sharedPreference)

}