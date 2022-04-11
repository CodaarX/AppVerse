package com.decagon.n26_p3_usecase.features.programmingJokes.di

import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import com.decagon.n26_p3_usecase.features.programmingJokes.remoteService.JokesApiService
import com.decagon.n26_p3_usecase.features.programmingJokes.utils.JokesConstants
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
    fun provideApiService(@Named("Jokes_Retrofit") retrofit : Retrofit) : JokesApiService {
        return retrofit.create(JokesApiService::class.java)
    }

    @Provides
    @Named("Jokes_Retrofit")
    @Singleton
    fun providesJokesRetrofit(converter : Converter.Factory, client : OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(JokesConstants.JOKES_BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .build()
    }

}