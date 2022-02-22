package com.decagon.n26_p3_usecase.core.di

import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideGsonConverter() : Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(converter : Converter.Factory, client : OkHttpClient, sharedPreference: SharedPreference): Retrofit {
        return Retrofit.Builder()
            .baseUrl(sharedPreference.loadFromSharedPref("String", "BASE_URL") as String)
            .client(client)
            .addConverterFactory(converter)
            .build()
    }


}