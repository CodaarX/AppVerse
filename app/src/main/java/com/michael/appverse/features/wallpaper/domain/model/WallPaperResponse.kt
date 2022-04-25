package com.michael.appverse.features.wallpaper.domain.model

data class WallPaperResponse(
    val results: MutableList<WallPaperData>,
    val total: Int,
    val total_pages: Int
)