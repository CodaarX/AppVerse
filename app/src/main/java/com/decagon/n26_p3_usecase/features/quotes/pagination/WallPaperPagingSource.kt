package com.decagon.n26_p3_usecase.features.quotes.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.repository.contract.WallpaperRepository
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCase
import com.decagon.n26_p3_usecase.features.wallpaper.usecase.WallPaperUseCaseImpl
import com.decagon.n26_p3_usecase.features.wallpaper.utils.WallPaperConstants
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
