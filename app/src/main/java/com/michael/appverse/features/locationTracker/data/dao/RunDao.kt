package com.michael.appverse.features.locationTracker.data.dao

import androidx.room.*
import com.michael.appverse.features.locationTracker.model.Run

@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(run: Run)

    @Delete
    suspend fun delete(run: Run)

    @Query("SELECT * FROM running_table ORDER BY timestamp DESC")
    suspend fun getAllRunSortedByDate(): List<Run>

    @Query("SELECT * FROM running_table ORDER BY timeInMillis DESC")
    suspend fun getAllRunSortedByTimeInMills(): List<Run>

    @Query("SELECT * FROM running_table ORDER BY caloriesBurned DESC")
    suspend fun getAllRunSortedByCaloriesBurned(): List<Run>

    @Query("SELECT * FROM running_table ORDER BY avgSpeed DESC")
    suspend fun getAllRunSortedByAverageSpeed(): List<Run>

    @Query("SELECT * FROM running_table ORDER BY distanceInMeters DESC")
    suspend fun getAllRunSortedByDistance(): List<Run>

    @Query("SELECT SUM(timeInMillis) FROM running_table")
    suspend fun getTotalTimeInMillis(): Long

    @Query("SELECT SUM(distanceInMeters) FROM running_table")
    suspend fun getTotalDistanceInMeters(): Int

    @Query("SELECT SUM(caloriesBurned) FROM running_table")
    suspend fun getTotalCaloriesBurned(): Int

    @Query("SELECT AVG(avgSpeed) FROM running_table")
    suspend fun getTotalAverageSpeed(): Long
//
//    @Query("SELECT * FROM running_table")
//    fun getTotalRuns(): LiveData<Float>
}