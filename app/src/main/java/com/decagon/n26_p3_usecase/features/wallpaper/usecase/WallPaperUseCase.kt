package com.decagon.n26_p3_usecase.features.wallpaper.usecase

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokesModelSafe
import com.decagon.n26_p3_usecase.features.programmingJokes.model.UiStateManager
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import kotlinx.coroutines.flow.Flow

interface WallPaperUseCase {
    suspend operator fun invoke(query: String, clientId: String, per_page: Int) : UiStateManager<MutableList<WallPaperDataSafe>>
}