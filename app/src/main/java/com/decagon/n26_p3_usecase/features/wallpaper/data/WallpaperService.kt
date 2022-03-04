package com.decagon.n26_p3_usecase.features.wallpaper.data

import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperService {

    @GET("photos")
    suspend fun getWallpaper(
        @Query("query") query: String,
        @Query("client_id") client_id: String,
        @Query("per_page") per_page: Int
    ): Response<WallPaperResponse>


}