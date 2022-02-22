package com.decagon.n26_p3_usecase.features.locationTracker.di

import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.contract.IRunningRepository
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation.RunningTrackerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RunningTrackerDeps {


    @Singleton
    @Provides
    fun provideRunningTrackerRepository(database: AppVerseDatabase): RunningTrackerRepositoryImpl {
        return RunningTrackerRepositoryImpl(database)
    }
}