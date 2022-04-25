package com.michael.appverse.features.wallpaper.data

import androidx.room.*
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM wallpaper_data")
    suspend fun getAllData() : MutableList<WallPaperDataSafe>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertData(data: WallPaperDataSafe)

    @Delete
    suspend fun deleteData(data: WallPaperDataSafe)

}