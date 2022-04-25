package com.michael.appverse.features.wallpaper.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.bitmap.BitmapPool
import coil.load
import coil.size.Size
import coil.transform.Transformation
import com.michael.appverse.R
import com.michael.appverse.databinding.FragmentWallPaperBinding
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe
import com.michael.appverse.features.wallpaper.presentation.viewModel.WallPaperViewModel
import com.example.mike_utils.MikeGlide

class FavouriteAdapter (val viewModel: WallPaperViewModel) : RecyclerView.Adapter<FavouriteAdapter.FavouriteWallPaperViewHolder>() {

    fun setWallPaperList(list: MutableList<WallPaperDataSafe>) {
        differ.submitList(list)
    }


    inner class FavouriteWallPaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(wallPaperDataSafe: WallPaperDataSafe) {

            val binding = FragmentWallPaperBinding.inflate(LayoutInflater.from(itemView.context), itemView as ViewGroup, false)
            val imageView: ImageView = itemView.findViewById(R.id.favourite_image_view)
            val cardView: CardView = itemView.findViewById(R.id.favourite_cardView_2)
            val favButton : ImageView = itemView.findViewById(R.id.favourite_image_for_saved_data)
            val title: TextView = itemView.findViewById(R.id.favourite_title)

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

                title.text = wallPaperDataSafe.description
                favButton.setImageResource(R.drawable.ic_baseline_favorite_24)
            }

            favButton.setOnClickListener {
                    wallPaperDataSafe.apply {
                        if (this.isFavourite!!){
                            viewModel.deleteFromDataBase(this)
                        }
                        this.isFavourite = false
                        notifyDataSetChanged()
                    }
                    favButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteWallPaperViewHolder {

        return FavouriteWallPaperViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favourite_wall_paper_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavouriteWallPaperViewHolder, position: Int) {
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
