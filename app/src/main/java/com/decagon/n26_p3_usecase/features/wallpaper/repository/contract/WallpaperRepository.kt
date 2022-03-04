package com.decagon.n26_p3_usecase.features.wallpaper.repository.contract

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperResponse
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun getWallpaper(query: String, clientId: String, per_page: Int): Flow<Resource<WallPaperResponse>>
}