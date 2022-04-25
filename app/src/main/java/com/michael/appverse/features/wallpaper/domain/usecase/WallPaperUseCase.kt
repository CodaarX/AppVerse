package com.michael.appverse.features.wallpaper.domain.usecase

import com.michael.appverse.features.programmingJokes.model.UiStateManager
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe

interface WallPaperUseCase {
    suspend operator fun invoke(query: String, clientId: String, per_page: Int, pageNumber: Int) : UiStateManager<MutableList<WallPaperDataSafe>>

    suspend fun saveToDataBase(wallPaperDataSafe: WallPaperDataSafe) : UiStateManager<MutableList<WallPaperDataSafe>>

    suspend fun getAllWallPaper() : UiStateManager<MutableList<WallPaperDataSafe>>

    suspend fun deleteWallpaperFromDatabase(wallPaperDataSafe: WallPaperDataSafe) : UiStateManager<MutableList<WallPaperDataSafe>>

}