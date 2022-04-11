package com.decagon.n26_p3_usecase.features.wallpaper.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wallpaper_data")
data class WallPaperDataSafe(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val color: String?,
    val description: String?,
    val url: String?,
    var isFavourite: Boolean? = null,
    )