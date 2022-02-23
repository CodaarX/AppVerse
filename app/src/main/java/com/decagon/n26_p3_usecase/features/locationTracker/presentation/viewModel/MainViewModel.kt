package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.contract.IRunningRepository
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation.RunningTrackerRepositoryImpl
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor( private val repository: RunningTrackerRepositoryImpl) : ViewModel(){

    fun insertRun(run: Run) = viewModelScope.launch { repository.insertRun(run) }

}