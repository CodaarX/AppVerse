package com.michael.appverse.features.programmingJokes.di

import com.michael.appverse.features.programmingJokes.remoteService.JokesApiService
import com.michael.appverse.features.programmingJokes.repository.contracts.JokesRepository
import com.michael.appverse.features.programmingJokes.repository.implementations.JokesRepositoryImpl
import com.michael.appverse.features.programmingJokes.usecase.contracts.AnyJokesUseCase
import com.michael.appverse.features.programmingJokes.usecase.contracts.ProgrammingJokesUseCase
import com.michael.appverse.features.programmingJokes.usecase.implementations.AnyJokesUseCaseImpl
import com.michael.appverse.features.programmingJokes.usecase.implementations.ProgrammingJokesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProgrammingJokesRepositoryModule {
    @Provides
    @Singleton
    fun providesRepository(jokesApiService: JokesApiService) : JokesRepository = JokesRepositoryImpl(jokesApiService)

    @Provides
    @Singleton
    fun providesAnyJokesUseCase(repository: JokesRepository) : AnyJokesUseCase = AnyJokesUseCaseImpl(repository)

    @Provides
    @Singleton
    fun providesProgrammingJokesUseCase(repository: JokesRepository) : ProgrammingJokesUseCase = ProgrammingJokesUseCaseImpl(repository)

}