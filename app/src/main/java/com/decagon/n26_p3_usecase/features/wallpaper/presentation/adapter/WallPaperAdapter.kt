package com.decagon.n26_p3_usecase.features.wallpaper.presentation.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.bitmap.BitmapPool
import coil.load
import coil.size.Size
import coil.transform.Transformation
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.databinding.FragmentWallPaperBinding
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewModel.WallPaperViewModel
import com.example.mike_utils.MikeGlide
import com.google.android.material.color.MaterialColors.getColor

class WallPaperAdapter(val viewModel: WallPaperViewModel) : RecyclerView.Adapter<WallPaperAdapter.WallPaperViewHolder>() {

    fun setWallPaperList(list: MutableList<WallPaperDataSafe>) {
        differ.submitList(list.shuffled())
    }


    inner class WallPaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(wallPaperDataSafe: WallPaperDataSafe) {

            val binding = FragmentWallPaperBinding.inflate(LayoutInflater.from(itemView.context), itemView as ViewGroup, false)
            val imageView: ImageView = itemView.findViewById(R.id.wallpaper_image_view)
            val cardView: CardView = itemView.findViewById(R.id.wall_paper_cardView)
            val favButton : ImageView = itemView.findViewById(R.id.favourite_image)


            MikeGlide.setImage(imageView, wallPaperDataSafe.url).also {
                imageView.load(wallPaperDataSafe.url) {
                    crossfade(true)
                    transformations(object : Transformation {
                        override fun key() = "paletteTransformer"
                        override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
                            Palette.from(input).generate { palette: Palette? ->
                                val color =  palette?.vibrantSwatch?.rgb ?: ContextCompat.getColor(imageView.context, R.color.white)

                                cardView.setCardBackgroundColor(color)
                                binding.wallPaperFragmentRoot.setBackgroundColor(color)
                            }
                            return input
                        }
                    })
                }

                if (wallPaperDataSafe.isFavourite == true) {
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }

            favButton.setOnClickListener {
                if (wallPaperDataSafe.isFavourite == true) {
                    wallPaperDataSafe.apply {
                        this.isFavourite = false
                        viewModel.deleteFromDataBase(this)
                    }
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    wallPaperDataSafe.apply {
                        this.isFavourite = true
                        viewModel.saveToDataBase(this)
                    }
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallPaperViewHolder {

        return WallPaperViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_slider_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WallPaperViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val diffCallBack = object : DiffUtil.ItemCallback<WallPaperDataSafe>() {

        override fun areItemsTheSame(
            oldItem: WallPaperDataSafe,
            newItem: WallPaperDataSafe
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WallPaperDataSafe,
            newItem: WallPaperDataSafe
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallBack)
}

