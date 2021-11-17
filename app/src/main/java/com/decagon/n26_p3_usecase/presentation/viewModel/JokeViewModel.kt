package com.decagon.n26_p3_usecase.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.data.repository.contracts.JokesRepository
import com.decagon.n26_p3_usecase.domain.model.JokeModel
import com.decagon.n26_p3_usecase.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(private val repository: JokesRepository) : ViewModel() {

    private var _jokeState = MutableLiveData<Resource<JokeModel>>()
    val jokeState : LiveData<Resource<JokeModel>> = _jokeState


    fun getProgrammingJokes(){
        _jokeState.value =  Resource.Loading("Loading")
        viewModelScope.launch {
            repository.getProgrammingJoke().collect {
                _jokeState.value = it
            }
        }
    }
}