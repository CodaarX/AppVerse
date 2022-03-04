package com.decagon.n26_p3_usecase.features.wallpaper.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import coil.bitmap.BitmapPool
import coil.load
import coil.size.Size
import coil.transform.Transformation
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.features.wallpaper.model.WallPaperDataSafe
import com.example.mike_utils.MikeGlide


class WallPaperAdapter : RecyclerView.Adapter<WallPaperAdapter.WallPaperViewHolder>() {

    var list = mutableListOf<WallPaperDataSafe>()

    @SuppressLint("NotifyDataSetChanged")
    fun setWallPaperList(list: MutableList<WallPaperDataSafe>) {
        this.list.clear()
        this.list = list
        notifyDataSetChanged()
    }

    inner class WallPaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(wallPaperDataSafe: WallPaperDataSafe) {

            val imageView: ImageView = itemView.findViewById(R.id.wallpaper_image_view)
            val cardView: CardView = itemView.findViewById(R.id.wall_paper_cardView)

            MikeGlide.setImage(imageView, wallPaperDataSafe.url)

            imageView.load(wallPaperDataSafe.url) {

                transformations(object : Transformation {
                    override fun key() = "paletteTransformer"
                    override suspend fun transform(
                        pool: BitmapPool,
                        input: Bitmap,
                        size: Size
                    ): Bitmap {

                        Palette.from(input).generate { palette: Palette? ->
                            // access palette instance here
                            cardView.setCardBackgroundColor(
                                palette?.vibrantSwatch?.rgb ?: ContextCompat.getColor(
                                    imageView.context,
                                    R.color.white
                                )
                            )
                        }
                        return input
                    }
                })
            }
//            // get bitmap from imageView
//            val bitmap = (holder.imageView.drawable as BitmapDrawable).bitmap
//            // use the palette to get the vibrant color
//            val palette = Palette.from(bitmap).generate()
//            //  Pick one of the swatches
//            val vibrant = palette.vibrantSwatch
//            // Set the background color of a layout based on the vibrant color
//            vibrant?.let {
//                holder.cardView.setBackgroundColor(it.rgb)
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallPaperViewHolder {
        return WallPaperViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_slider_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WallPaperViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

