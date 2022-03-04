package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation.RunningTrackerRepositoryImpl
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor( private val repository: RunningTrackerRepositoryImpl) : ViewModel(){

    // set mutableLiveData
    private val runList: MutableLiveData<List<Run>> = MutableLiveData()
    val runs: LiveData<List<Run>> = runList

    init {
        viewModelScope.launch {
            runList.postValue(repository.getAllRunsSortedByDate())
        }
    }

    fun sortRunsByDate() {
        viewModelScope.launch {
            runList.postValue(repository.getAllRunsSortedByDate())
        }
    }

    fun sortRunsByTime() {
        viewModelScope.launch {
            runList.value = repository.getAllRunsSortedByTime()
        }
    }

    fun sortRunsByDistance() {
        viewModelScope.launch {
            runList.value = repository.getAllRunsSortedByDistance()
        }
    }

    fun sortRunsByCalories() {
        viewModelScope.launch {
            runList.value = repository.getAllRunsSortedByCaloriesBurned()
        }
    }

    fun sortRunsByAvgSpeed() {
        viewModelScope.launch {
            runList.value = repository.getAllRunsSortedByAvgSpeed()
        }
    }

    fun deleteAllRuns() {
        viewModelScope.launch {
           // repository.deleteAllRuns()
        }
    }

    fun insertRun(run: Run) = viewModelScope.launch { repository.insertRun(run) }

}