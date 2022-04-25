package com.michael.appverse.features.locationTracker.di

import com.michael.appverse.core.data.local.AppVerseDatabase
import com.michael.appverse.features.locationTracker.data.mediator.implementation.RunningMediatorRepoImpl
import com.michael.appverse.features.locationTracker.data.repository.implementation.RunningTrackerRepositoryImpl
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

    @Singleton
    @Provides
    fun provideRunningMediatorRepository(database: AppVerseDatabase): RunningMediatorRepoImpl {
        return RunningMediatorRepoImpl(database)
    }

}