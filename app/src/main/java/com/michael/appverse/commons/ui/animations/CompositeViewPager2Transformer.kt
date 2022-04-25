package com.michael.appverse.commons.ui.animations

import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer

object CompositeViewPager2Transformer  {

    fun getTransformation(): CompositePageTransformer {

        val tramsform = CompositePageTransformer()

        tramsform.addTransformer(MarginPageTransformer(30))
        tramsform.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        return tramsform
    }


}