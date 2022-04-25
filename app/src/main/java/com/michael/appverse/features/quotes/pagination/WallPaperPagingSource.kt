package com.michael.appverse.features.quotes.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe
import com.michael.appverse.features.wallpaper.domain.contract.WallpaperRepository
import javax.inject.Inject


class WallPaperPagingSource @Inject constructor(private val repository: WallpaperRepository) : PagingSource<Int, WallPaperDataSafe>(){
    override fun getRefreshKey(state: PagingState<Int, WallPaperDataSafe>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallPaperDataSafe> {
//        val page = params.key ?: 1
//        val wallPaperData = repository.getWallpaper(page = page, per_page = params.loadSize, clientId = WallPaperConstants.clientId, query = "nature")
//        return LoadResult.Page(
//            data = wallPaperData,
//            prevKey = if (page == 1) null else page - 1,
//            nextKey = if (wallPaperDataSafe.data?.isEmpty() == true) null else page + 1
//        )
        TODO("Not yet implemented")
    }


}
