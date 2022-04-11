package com.decagon.n26_p3_usecase.features.quotes.pagination

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.bitmap.BitmapPool
import coil.load
import coil.size.Size
import coil.transform.Transformation
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.databinding.AdapterSliderLayoutBinding
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.example.mike_utils.MikeGlide

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


