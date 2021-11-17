package com.decagon.n26_p3_usecase.di

import com.decagon.n26_p3_usecase.data.remote.JokesApiService
import com.decagon.n26_p3_usecase.data.repository.contracts.JokesRepository
import com.decagon.n26_p3_usecase.data.repository.implementations.JokesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesRepository(jokesApiService: JokesApiService) : JokesRepository {
        return JokesRepositoryImpl(jokesApiService)
    }
}