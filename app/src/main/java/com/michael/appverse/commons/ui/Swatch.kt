package com.michael.appverse.commons.ui

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette

object Swatch {
    fun setBackground( view: ImageView, cardView: CardView) {

        // get bitmap from imageView
        val bitmap = (view.drawable as BitmapDrawable).bitmap
        // use the palette to get the vibrant color
        val palette = Palette.from(bitmap).generate()
        //  Pick one of the swatches
        val vibrant = palette.vibrantSwatch
        // Set the background color of a layout based on the vibrant color
        vibrant?.let {
            cardView.setBackgroundColor(it.rgb)
        }
    }

}