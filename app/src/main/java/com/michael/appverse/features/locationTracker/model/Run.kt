package com.michael.appverse.features.locationTracker.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "running_table")
data class Run(
    var image: Bitmap? = null,
    var timestamp: Long = 0L, // when run was
    var avgSpeed: Float = 0f,
    var distanceInMeters: Int = 0,
    var caloriesBurned: Int = 0,
    var timeInMillis: Long = 0L, // how long
) {
    @PrimaryKey(autoGenerate = true) // for room to handle generating the id and we can create a new run without having to pass the id
    var id: Int? = null

}
