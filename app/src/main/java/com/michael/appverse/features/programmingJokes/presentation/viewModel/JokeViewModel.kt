package com.michael.appverse.features.programmingJokes.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michael.appverse.commons.utils.Resource
import com.michael.appverse.features.programmingJokes.model.JokesModelSafe
import com.michael.appverse.features.programmingJokes.usecase.contracts.AnyJokesUseCase
import com.michael.appverse.features.programmingJokes.usecase.contracts.ProgrammingJokesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val anyJokesUseCase: AnyJokesUseCase,
    private val programmingJokesUseCase: ProgrammingJokesUseCase
    ) : ViewModel() {

    private var _jokeState = MutableLiveData<Resource<JokesModelSafe>>()
    val jokeState : LiveData<Resource<JokesModelSafe>> = _jokeState
    
    fun getProgrammingJokes(){
        _jokeState.value =  Resource.Loading("Loading")
        viewModelScope.launch {
            programmingJokesUseCase.invoke().collect {
                _jokeState.value = it
            }
        }
    }

    fun getAnyJokes(){
        _jokeState.value =  Resource.Loading("Loading")
        viewModelScope.launch {
            anyJokesUseCase.invoke().collect {
                _jokeState.value = it
            }
        }
    }

}