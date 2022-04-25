package com.michael.appverse.features.wallpaper.data.repository.impl

import com.michael.appverse.commons.utils.Resource
import com.michael.appverse.core.data.BaseRepositoryRemoteOperation
import com.michael.appverse.features.quotes.data.WallpaperService
import com.michael.appverse.features.todo.data.repository.implementation.baseRepository
import com.michael.appverse.features.wallpaper.data.WallpaperDao
import com.michael.appverse.features.wallpaper.domain.contract.WallpaperRepository
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe
import com.michael.appverse.features.wallpaper.domain.model.WallPaperResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WallPaperRepositoryImpl @Inject constructor(private val wallpaperService: WallpaperService, private val wallpaperDao: WallpaperDao) : WallpaperRepository, BaseRepositoryRemoteOperation() {

    override suspend fun getWallpaper(query: String, clientId: String, per_page: Int, page: Int): Flow<Resource<WallPaperResponse>> =

        getData { wallpaperService.getWallpaper(query, clientId, per_page, page) }

    override suspend fun saveToDatabase(wallpaper: WallPaperDataSafe): Flow<MutableList<WallPaperDataSafe>> =
        baseRepository(
            fetchFromLocal = { getWallpapersFromDatabase() },
            performOperation = { wallpaperDao.insertData(wallpaper) }
        )

    override suspend fun getWallpaperFromDatabase(): Flow<MutableList<WallPaperDataSafe>> =
        flow {
            emit(getWallpapersFromDatabase())
        }

    override suspend fun deleteWallpaperFromDatabase(wallpaper: WallPaperDataSafe): Flow<MutableList<WallPaperDataSafe>> =
        baseRepository(
            fetchFromLocal = { getWallpapersFromDatabase() },
            performOperation = { wallpaperDao.deleteData(wallpaper) }
        )

    private suspend fun getWallpapersFromDatabase(): MutableList<WallPaperDataSafe> = wallpaperDao.getAllData()
}
