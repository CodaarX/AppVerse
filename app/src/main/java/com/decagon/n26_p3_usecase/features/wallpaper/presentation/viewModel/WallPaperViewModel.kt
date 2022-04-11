package com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.features.programmingJokes.model.UiStateManager
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallPaperViewModel @Inject constructor(private val wallPaperUseCaseImpl: WallPaperUseCaseImpl) : ViewModel() {

    private val _wallPapers = MutableLiveData<UiStateManager<MutableList<WallPaperDataSafe>>>()
    val wallPapers: LiveData<UiStateManager<MutableList<WallPaperDataSafe>>>
        get() = _wallPapers

    private val _favWallPapers = MutableLiveData<UiStateManager<MutableList<WallPaperDataSafe>>>()
    val favWallPapers: LiveData<UiStateManager<MutableList<WallPaperDataSafe>>>
        get() = _favWallPapers

    var currentQuery: String = ""

    fun getWallPaperList(query: String, clientId: String, per_page: Int, pageNumber: Int) {
        viewModelScope.launch {
            delay(3000)
            _wallPapers.value = wallPaperUseCaseImpl.invoke(query, clientId, per_page, pageNumber)
        }
    }

    fun saveToDataBase(wallPaperDataSafe: WallPaperDataSafe) {
        viewModelScope.launch {
            _favWallPapers.value = wallPaperUseCaseImpl.saveToDataBase(wallPaperDataSafe)
        }
    }

    fun deleteFromDataBase(wallPaperDataSafe: WallPaperDataSafe) {
        viewModelScope.launch {
            _favWallPapers.value = wallPaperUseCaseImpl.deleteWallpaperFromDatabase(wallPaperDataSafe)
        }
    }

    fun getWallPaperFromDatabase() {
        viewModelScope.launch {
            _favWallPapers.value = wallPaperUseCaseImpl.getAllWallPaper()
        }
    }
}
