package com.decagon.n26_p3_usecase.features.wallpaper.data

import androidx.room.*
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM wallpaper_data")
    suspend fun getAllData() : MutableList<WallPaperDataSafe>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertData(data: WallPaperDataSafe)

    @Delete
    suspend fun deleteData(data: WallPaperDataSafe)

}