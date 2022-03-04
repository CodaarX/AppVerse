package com.decagon.n26_p3_usecase.features.wallpaper.usecase

import com.decagon.n26_p3_usecase.commons.ui.log
import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.UiStateManager
import com.decagon.n26_p3_usecase.features.wallpaper.WallPaperConstants
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.model.toWallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import com.decagon.n26_p3_usecase.features.wallpaper.repository.impl.WallPaperRepositoryImpl
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class WallPaperUseCaseImpl @Inject constructor(private val wallpaperRepository: WallpaperRepository)  : WallPaperUseCase {

    override suspend fun invoke(query: String, clientId: String, per_page: Int): UiStateManager<MutableList<WallPaperDataSafe>> {

        var result : UiStateManager<MutableList<WallPaperDataSafe>> = UiStateManager()

        wallpaperRepository.getWallpaper(query, clientId, per_page).collect {
            when(it){
                is Resource.Success -> {
                    val data = it.data.results.map { wallPaperData ->
                        wallPaperData.toWallPaperDataSafe()
                    }
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
                    result =UiStateManager(false, null, null)
                }
            }
        }
        return result
    }
}