package com.decagon.n26_p3_usecase.features.locationTracker.data.repository.contract

import androidx.lifecycle.LiveData
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run

interface IRunningRepository {
    suspend fun insertRun(run: Run)
    suspend fun deleteRun(run: Run)
    fun getAllRunsSortedByDate(): LiveData<List<Run>>
    fun getAllRunsSortedByDistance(): LiveData<List<Run>>
    fun getAllRunsSortedByTime(): LiveData<List<Run>>
    fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>>
    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>>
    fun getTotalTimeInMillis(): LiveData<Long>
    fun getTotalDistanceInMeters(): LiveData<Int>
    fun getTotalCaloriesBurned(): LiveData<Int>
    fun getTotalAvgSpeed(): LiveData<Long>
//    fun getTotalRuns(): LiveData<Float>

}