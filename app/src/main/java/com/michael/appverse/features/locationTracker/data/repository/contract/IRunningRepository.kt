package com.michael.appverse.features.locationTracker.data.repository.contract

import com.michael.appverse.features.locationTracker.model.Run

interface IRunningRepository {
    suspend fun insertRun(run: Run)
    suspend fun deleteRun(run: Run)
    suspend fun getAllRunsSortedByDate(): List<Run>
    suspend fun getAllRunsSortedByDistance(): List<Run>
    suspend fun getAllRunsSortedByTime(): List<Run>
    suspend fun getAllRunsSortedByAvgSpeed(): List<Run>
    suspend fun getAllRunsSortedByCaloriesBurned(): List<Run>
    suspend fun getTotalTimeInMillis(): Long
    suspend fun getTotalDistanceInMeters(): Int
    suspend fun getTotalCaloriesBurned(): Int
    suspend fun getTotalAvgSpeed(): Long
//    fun getTotalRuns(): LiveData<Float>

}