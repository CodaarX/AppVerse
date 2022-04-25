package com.michael.appverse.features.quotes.pagination

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe

class PagedWallPaperAdapter : PagedListAdapter<WallPaperDataSafe, WallPaperViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallPaperViewHolder {
        return WallPaperViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WallPaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) } ?: return
    }

}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WallPaperDataSafe>() {
    override fun areItemsTheSame(oldItem: WallPaperDataSafe, newItem: WallPaperDataSafe) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WallPaperDataSafe, newItem: WallPaperDataSafe) =
        oldItem == newItem

}


