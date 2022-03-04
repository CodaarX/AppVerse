package com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.SortType
import com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.implementation.RunningMediatorRepoImpl
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunMediatorViewModel @Inject constructor(private val mediatorReop: RunningMediatorRepoImpl) : ViewModel() {

    // MediatorLiveData is a LiveData that exposes a single mutable value.
    // It is used to observe multiple LiveData objects and react to changes in all of them.
    val runs = MediatorLiveData<List<Run>>()

    private val runsSortedbyDate = mediatorReop.getAllRunsSortedByDate()
    private val runsSortedbyTime = mediatorReop.getAllRunsSortedByTime()
    private val runsSortedbyDistance = mediatorReop.getAllRunsSortedByDistance()
    private val runsSortedbySpeed = mediatorReop.getAllRunsSortedByAvgSpeed()
    private val runsSortedbyCaloriesBurned = mediatorReop.getAllRunsSortedByCaloriesBurned()

    var sortType = SortType.DATE


    // add source to the MediatorLiveData to observe the LiveData objects concurrently
    init {
        runs.addSource(runsSortedbyDate) { result ->
            if (sortType == SortType.DATE)
                result?.let {
                    runs.value = it
                }
        }


        runs.addSource(runsSortedbyDistance) { result ->
            if (sortType == SortType.DISTANCE)
                result?.let {
                    runs.value = it
                }
        }

        runs.addSource(runsSortedbySpeed) { result ->
            if (sortType == SortType.AVG_SPEED)
                result?.let {
                    runs.value = it
                }
        }

        runs.addSource(runsSortedbyTime) { result ->
            if (sortType == SortType.RUNNING_TIME)
                result?.let {
                    runs.value = it
                }
        }
        runs.addSource(runsSortedbyCaloriesBurned) { result ->
            if (sortType == SortType.CALORIES_BURNED)
                result?.let {
                    runs.value = it
                }
        }
    }


    // update mediator live data when sort type is changed with the desired value
    fun sortRuns(type: SortType) = when(type){
        SortType.DATE -> runsSortedbyDate.value?.let { runs.value = it }
        SortType.RUNNING_TIME -> runsSortedbyTime.value?.let { runs.value = it }
        SortType.CALORIES_BURNED -> runsSortedbyCaloriesBurned.value?.let { runs.value = it }
        SortType.DISTANCE -> runsSortedbyDistance.value?.let { runs.value = it }
        SortType.AVG_SPEED -> runsSortedbySpeed.value?.let { runs.value = it }
    }.also {
        this.sortType = type // update the sort type
    }

    fun insertRun(run: Run) {
        viewModelScope.launch {
            mediatorReop.insertRun(run)
        }
    }

    fun deleteAllRuns() {}


}