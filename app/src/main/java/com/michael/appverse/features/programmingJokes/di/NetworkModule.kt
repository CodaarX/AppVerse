package com.michael.appverse.features.programmingJokes.di

import com.michael.appverse.features.programmingJokes.remoteService.JokesApiService
import com.michael.appverse.features.programmingJokes.utils.JokesConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(@Named("Jokes_Retrofit") retrofit: Retrofit): JokesApiService {
        return retrofit.create(JokesApiService::class.java)
    }

    @Provides
    @Named("Jokes_Retrofit")
    @Singleton
    fun providesJokesRetrofit(converter: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(JokesConstants.JOKES_BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .build()
    }
}
