package com.michael.appverse.features.locationTracker.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.michael.appverse.features.locationTracker.data.mediator.implementation.RunningMediatorRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: RunningMediatorRepoImpl) : ViewModel(){


            val totalTimeRun = repository.getTotalTimeInMillis()
            val totalDistance = repository.getTotalDistanceInMeters()
            val totalCalories = repository.getTotalCaloriesBurned()
            val totalAvgSpeed = repository.getTotalAvgSpeed()

            val runSortedByDate = repository.getAllRunsSortedByDate()


}