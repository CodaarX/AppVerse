package com.michael.appverse.features.quotes.data

import com.michael.appverse.features.wallpaper.domain.model.WallPaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperService {

    @GET("photos")
    suspend fun getWallpaper(
        @Query("query") query: String,
        @Query("client_id") client_id: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Response<WallPaperResponse>
}
