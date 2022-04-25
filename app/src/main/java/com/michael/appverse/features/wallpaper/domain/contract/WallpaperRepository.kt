package com.michael.appverse.features.wallpaper.domain.contract

import com.michael.appverse.commons.utils.Resource
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe
import com.michael.appverse.features.wallpaper.domain.model.WallPaperResponse
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun getWallpaper(query: String, clientId: String, per_page: Int,  page: Int): Flow<Resource<WallPaperResponse>>

    suspend fun saveToDatabase(wallpaper: WallPaperDataSafe) : Flow<MutableList<WallPaperDataSafe>>

    suspend fun getWallpaperFromDatabase(): Flow<MutableList<WallPaperDataSafe>>

    suspend fun deleteWallpaperFromDatabase(wallpaper: WallPaperDataSafe) : Flow<MutableList<WallPaperDataSafe>>
}