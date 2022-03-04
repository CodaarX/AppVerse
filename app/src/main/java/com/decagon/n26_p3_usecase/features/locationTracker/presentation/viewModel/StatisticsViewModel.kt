package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.implementation.RunningMediatorRepoImpl
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation.RunningTrackerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: RunningMediatorRepoImpl) : ViewModel(){


            val totalTimeRun = repository.getTotalTimeInMillis()
            val totalDistance = repository.getTotalDistanceInMeters()
            val totalCalories = repository.getTotalCaloriesBurned()
            val totalAvgSpeed = repository.getTotalAvgSpeed()

            val runSortedByDate = repository.getAllRunsSortedByDate()


}