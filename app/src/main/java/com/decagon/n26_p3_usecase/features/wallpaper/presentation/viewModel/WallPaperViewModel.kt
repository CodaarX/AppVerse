package com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.features.programmingJokes.model.UiStateManager
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCaseImpl
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallPaperViewModel @Inject constructor(private val wallPaperUseCaseImpl: WallPaperUseCaseImpl) : ViewModel() {

    private val _wallPapers = MutableLiveData<UiStateManager<MutableList<WallPaperDataSafe>>>()
    val wallPapers : LiveData<UiStateManager<MutableList<WallPaperDataSafe>>>
        get() = _wallPapers


    fun getWallPaperList(query: String, clientId: String, per_page: Int){
        viewModelScope.launch {
            _wallPapers.value = wallPaperUseCaseImpl.invoke(query, clientId, per_page)
        }
    }
}

