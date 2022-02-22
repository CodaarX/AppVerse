package com.decagon.n26_p3_usecase.features.locationTracker.data.repository.implementation

import androidx.lifecycle.LiveData
import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.features.locationTracker.data.repository.contract.IRunningRepository
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import javax.inject.Inject

class RunningTrackerRepositoryImpl @Inject constructor (private val database: AppVerseDatabase) : IRunningRepository{

    override suspend fun insertRun(run: Run) = database.runDao().insert(run)

    override suspend fun deleteRun(run: Run) = database.runDao().delete(run)

    override fun getAllRunsSortedByDate(): LiveData<List<Run>> = database.runDao().getAllRunSortedByDate()

    override fun getAllRunsSortedByDistance(): LiveData<List<Run>> = database.runDao().getAllRunSortedByDistance()

    override fun getAllRunsSortedByTime(): LiveData<List<Run>> = database.runDao().getAllRunSortedByTimeInMills()

    override fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>> = database.runDao().getAllRunSortedByAverageSpeed()

    override fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>> = database.runDao().getAllRunSortedByCaloriesBurned()

    override fun getTotalTimeInMillis(): LiveData<Long> =  database.runDao().getTotalTimeInMillis()

    override fun getTotalDistanceInMeters(): LiveData<Int> = database.runDao().getTotalDistanceInMeters()

    override fun getTotalCaloriesBurned(): LiveData<Int> = database.runDao().getTotalCaloriesBurned()

    override fun getTotalAvgSpeed(): LiveData<Long> = database.runDao().getTotalAverageSpeed()

//    override fun getTotalRuns(): LiveData<Float> = database.runDao().getTotalRuns()

}