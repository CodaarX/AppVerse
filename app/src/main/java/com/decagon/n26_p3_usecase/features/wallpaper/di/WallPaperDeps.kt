package com.decagon.n26_p3_usecase.features.wallpaper.di

import com.decagon.n26_p3_usecase.features.programmingJokes.remoteService.JokesApiService
import com.decagon.n26_p3_usecase.features.programmingJokes.repository.contracts.JokesRepository
import com.decagon.n26_p3_usecase.features.programmingJokes.repository.implementations.JokesRepositoryImpl
import com.decagon.n26_p3_usecase.features.programmingJokes.usecase.contracts.AnyJokesUseCase
import com.decagon.n26_p3_usecase.features.programmingJokes.usecase.contracts.ProgrammingJokesUseCase
import com.decagon.n26_p3_usecase.features.programmingJokes.usecase.implementations.AnyJokesUseCaseImpl
import com.decagon.n26_p3_usecase.features.programmingJokes.usecase.implementations.ProgrammingJokesUseCaseImpl
import com.decagon.n26_p3_usecase.features.wallpaper.data.WallpaperService
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import com.decagon.n26_p3_usecase.features.wallpaper.repository.impl.WallPaperRepositoryImpl
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCase
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WallPaperDeps {
    @Provides
    @Singleton
    fun provideWalPaperService(retrofit : Retrofit) : WallpaperService {
        return retrofit.create(WallpaperService::class.java)
    }


    @Provides
    @Singleton
    fun providesWallPaperRepository(wallpaperService: WallpaperService) : WallpaperRepository = WallPaperRepositoryImpl(wallpaperService)


    @Provides
    @Singleton
    fun providesWallPaperUseCase(wallpaperRepository: WallpaperRepository) : WallPaperUseCase = WallPaperUseCaseImpl(wallpaperRepository)


}