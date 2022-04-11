package com.decagon.n26_p3_usecase.features.quotes.pagination

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.bitmap.BitmapPool
import coil.load
import coil.size.Size
import coil.transform.Transformation
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.databinding.AdapterSliderLayoutBinding
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.example.mike_utils.MikeGlide


class WallPaperViewHolder(val binding: AdapterSliderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(wallPaperDataSafe: WallPaperDataSafe) {

        val imageView: ImageView = binding.wallpaperImageView
        val cardView: CardView = binding.wallPaperCardView

        MikeGlide.setImage(imageView, wallPaperDataSafe.url)

        imageView.load(wallPaperDataSafe.url) {
            transformations(object : Transformation {
                override fun key() = "paletteTransformer"
                override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {
                    Palette.from(input).generate { palette: Palette? ->
                        // access palette instance here
                        cardView.setCardBackgroundColor(
                            palette?.vibrantSwatch?.rgb ?: ContextCompat.getColor(imageView.context, R.color.white)
                        )
                    }
                    return input
                }
            })
        }
    }


    companion object {
        fun create(parent: ViewGroup): WallPaperViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AdapterSliderLayoutBinding.inflate(layoutInflater, parent, false)
            return WallPaperViewHolder(binding)
        }
    }

}
