package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation.RunningTrackerRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: RunningTrackerRepositoryImpl) : ViewModel(){

}