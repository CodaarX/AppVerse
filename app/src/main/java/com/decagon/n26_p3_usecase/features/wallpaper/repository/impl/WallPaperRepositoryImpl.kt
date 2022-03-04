package com.decagon.n26_p3_usecase.features.wallpaper.repository.impl

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.core.data.BaseRepositoryRemoteOperation
import com.decagon.n26_p3_usecase.features.wallpaper.data.WallpaperService
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperResponse
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallPaperRepositoryImpl @Inject constructor(private val wallpaperService: WallpaperService) : WallpaperRepository, BaseRepositoryRemoteOperation() {

    override suspend fun getWallpaper(query: String, clientId: String, per_page: Int): Flow<Resource<WallPaperResponse>> =

        getData {
            wallpaperService.getWallpaper(query, clientId, per_page)
        }

}