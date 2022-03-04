package com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.implementation

import androidx.lifecycle.LiveData
import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.features.locationTracker.data.mediator.contract.IMediatorRunningRepository
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import javax.inject.Inject

class RunningMediatorRepoImpl @Inject constructor (private val database: AppVerseDatabase) :
    IMediatorRunningRepository {

    override suspend fun insertRun(run: Run) = database.runMediatorDao().insert(run)

    override suspend fun deleteRun(run: Run) = database.runMediatorDao().delete(run)

    override fun getAllRunsSortedByDate(): LiveData<List<Run>> =
        database.runMediatorDao().getAllRunSortedByDate()

    override fun getAllRunsSortedByDistance(): LiveData<List<Run>> =
        database.runMediatorDao().getAllRunSortedByDistance()

    override fun getAllRunsSortedByTime(): LiveData<List<Run>> =
        database.runMediatorDao().getAllRunSortedByTimeInMills()

    override fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>> =
        database.runMediatorDao().getAllRunSortedByAverageSpeed()

    override fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>> =
        database.runMediatorDao().getAllRunSortedByCaloriesBurned()

    override fun getTotalTimeInMillis(): LiveData<Long> = database.runMediatorDao().getTotalTimeInMillis()

    override fun getTotalDistanceInMeters(): LiveData<Int> =
        database.runMediatorDao().getTotalDistanceInMeters()

    override fun getTotalCaloriesBurned(): LiveData<Int> = database.runMediatorDao().getTotalCaloriesBurned()

    override fun getTotalAvgSpeed(): LiveData<Long> = database.runMediatorDao().getTotalAverageSpeed()

//    override fun getTotalRuns(): LiveData<Float> = database.runDao().getTotalRuns()
}
