package com.decagon.n26_p3_usecase.features.wallpaper.repository.impl

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.core.data.BaseRepositoryRemoteOperation
import com.decagon.n26_p3_usecase.features.quotes.data.WallpaperService
import com.decagon.n26_p3_usecase.features.todo.data.repository.implementation.baseRepository
import com.decagon.n26_p3_usecase.features.wallpaper.data.WallpaperDao
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperResponse
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WallPaperRepositoryImpl @Inject constructor(private val wallpaperService: WallpaperService, private val wallpaperDao: WallpaperDao) : WallpaperRepository, BaseRepositoryRemoteOperation() {

    override suspend fun getWallpaper(query: String, clientId: String, per_page: Int, page: Int): Flow<Resource<WallPaperResponse>> =

        getData { wallpaperService.getWallpaper(query, clientId, per_page, page) }

    override suspend fun saveToDatabase(wallpaper: WallPaperDataSafe) : Flow<MutableList<WallPaperDataSafe>> =
        baseRepository(
            fetchFromLocal = { getWallpapersFromDatabase() },
            performOperation = { wallpaperDao.insertData(wallpaper)}
        )


    override suspend fun getWallpaperFromDatabase(): Flow<MutableList<WallPaperDataSafe>> =
        flow {
            emit(getWallpapersFromDatabase())
        }

    override suspend fun deleteWallpaperFromDatabase(wallpaper: WallPaperDataSafe): Flow<MutableList<WallPaperDataSafe>> =
        baseRepository(
            fetchFromLocal = { getWallpapersFromDatabase() },
            performOperation = {  wallpaperDao.deleteData(wallpaper) }
        )


    private suspend fun getWallpapersFromDatabase(): MutableList<WallPaperDataSafe> = wallpaperDao.getAllData()


}