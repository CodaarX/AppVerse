package com.decagon.n26_p3_usecase.features.programmingJokes.di

import com.decagon.n26_p3_usecase.features.programmingJokes.remoteService.JokesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit : Retrofit) : JokesApiService {
        return retrofit.create(JokesApiService::class.java)
    }

}