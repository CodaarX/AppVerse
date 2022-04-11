package com.decagon.n26_p3_usecase.features.wallpaper.usecase

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.UiStateManager
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.model.toWallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class WallPaperUseCaseImpl @Inject constructor(
    private val wallpaperRepository: WallpaperRepository
) : WallPaperUseCase {
    var result: UiStateManager<MutableList<WallPaperDataSafe>> = UiStateManager()
    var data = mutableListOf<WallPaperDataSafe>()

    override suspend fun invoke(
        query: String,
        clientId: String,
        per_page: Int,
        pageNumber: Int
    ): UiStateManager<MutableList<WallPaperDataSafe>> {

        wallpaperRepository.getWallpaper(query, clientId, per_page, pageNumber).collect {
            when (it) {
                is Resource.Success -> {
                    data = it.data.results.map { mapped -> mapped.toWallPaperDataSafe() }
                        .toMutableList()
//                        .filter { wallPaperData ->
//                            wallPaperData.height > wallPaperData.width
//                        }

                    result = UiStateManager(false, data.toMutableList(), null)
                }
                is Resource.Error -> {
                    result = UiStateManager(false, null, it.message)
                }
                is Resource.Loading -> {
                    result = UiStateManager(true, null, null)
                }
                else -> {
                    result = UiStateManager(false, null, null)
                }
            }
        }
        return result
    }

    override suspend fun saveToDataBase(wallPaperDataSafe: WallPaperDataSafe): UiStateManager<MutableList<WallPaperDataSafe>> {
        wallpaperRepository.saveToDatabase(wallPaperDataSafe).collect {
            result = UiStateManager(false, it, null)
        }
        return result
    }

    override suspend fun getAllWallPaper(): UiStateManager<MutableList<WallPaperDataSafe>> {
        wallpaperRepository.getWallpaperFromDatabase().collect {
            result = UiStateManager(false, it, null)
        }
        return result
    }

    override suspend fun deleteWallpaperFromDatabase(wallPaperDataSafe: WallPaperDataSafe): UiStateManager<MutableList<WallPaperDataSafe>> {
        wallpaperRepository.deleteWallpaperFromDatabase(wallPaperDataSafe).collect {
            result = UiStateManager(false, it, null)
        }
        return result
    }
}
