package com.decagon.n26_p3_usecase.features.wallpaper.di

import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import com.decagon.n26_p3_usecase.features.programmingJokes.utils.JokesConstants
import com.decagon.n26_p3_usecase.features.quotes.data.WallpaperService
import com.decagon.n26_p3_usecase.features.wallpaper.data.WallpaperDao
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import com.decagon.n26_p3_usecase.features.wallpaper.repository.impl.WallPaperRepositoryImpl
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCase
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCaseImpl
import com.decagon.n26_p3_usecase.features.wallpaper.utils.WallPaperConstants
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
object WallPaperDeps {
    @Provides
    @Singleton
    fun provideWalPaperService(@Named("WallPaper_Retrofit") retrofit : Retrofit) : WallpaperService = retrofit.create(WallpaperService::class.java)

    @Provides
    @Singleton
    fun providesWallPaperRepository(wallpaperService: WallpaperService, wallpaperDao: WallpaperDao) : WallpaperRepository = WallPaperRepositoryImpl(wallpaperService, wallpaperDao)

    @Provides
    @Singleton
    fun providesWallPaperUseCase(wallpaperRepository: WallpaperRepository) : WallPaperUseCase = WallPaperUseCaseImpl(wallpaperRepository)

    @Provides
    @Singleton
    fun provideWallPaperDao(database: AppVerseDatabase) : WallpaperDao = database.wallPaperDao()

    @Provides
    @Named("WallPaper_Retrofit")
    @Singleton
    fun providesWallPaperRetrofit(converter : Converter.Factory, client : OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(WallPaperConstants.wallPaperBaseUrl)
            .client(client)
            .addConverterFactory(converter)
            .build()
    }

}