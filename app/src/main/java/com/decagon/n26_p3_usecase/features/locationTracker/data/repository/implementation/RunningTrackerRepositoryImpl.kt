package com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation

import androidx.lifecycle.LiveData
import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.contract.IRunningRepository
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import javax.inject.Inject

class RunningTrackerRepositoryImpl @Inject constructor (private val database: AppVerseDatabase) : IRunningRepository{

    override suspend fun insertRun(run: Run) = database.runDao().insert(run)

    override suspend fun deleteRun(run: Run) = database.runDao().delete(run)

    override suspend fun getAllRunsSortedByDate(): List<Run> = database.runDao().getAllRunSortedByDate()

    override suspend fun getAllRunsSortedByDistance(): List<Run> = database.runDao().getAllRunSortedByDistance()

    override suspend fun getAllRunsSortedByTime(): List<Run> = database.runDao().getAllRunSortedByTimeInMills()

    override suspend fun getAllRunsSortedByAvgSpeed(): List<Run> = database.runDao().getAllRunSortedByAverageSpeed()

    override suspend fun getAllRunsSortedByCaloriesBurned(): List<Run> = database.runDao().getAllRunSortedByCaloriesBurned()

    override suspend fun getTotalTimeInMillis(): Long =  database.runDao().getTotalTimeInMillis()

    override suspend fun getTotalDistanceInMeters(): Int = database.runDao().getTotalDistanceInMeters()

    override suspend fun getTotalCaloriesBurned(): Int = database.runDao().getTotalCaloriesBurned()

    override suspend fun getTotalAvgSpeed(): Long = database.runDao().getTotalAverageSpeed()

//    override fun getTotalRuns(): LiveData<Float> = database.runDao().getTotalRuns()

}