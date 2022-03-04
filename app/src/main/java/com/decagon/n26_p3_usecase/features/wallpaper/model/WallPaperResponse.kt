package com.decagon.n26_p3_usecase.features.wallpaper.model

data class WallPaperResponse(
    val results: List<WallPaperData>,
    val total: Int,
    val total_pages: Int
)