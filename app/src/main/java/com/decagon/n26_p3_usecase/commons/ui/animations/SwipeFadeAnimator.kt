package com.decagon.edconnect.utils.animations

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


object SwipeFadeAnimator : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.translationX = -position * page.width
        page.pivotX = 0F
        page.pivotY = 0F
        if (position < -1) {    // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.alpha = 0F
        } else if (position <= 0) {    // [-1,0]
            page.rotation = 90 * abs(position)
            page.alpha = 1 - abs(position)
        } else if (position <= 1) {    // (0,1]
            page.rotation = 0F
            page.alpha = 1F
        } else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.alpha = 0F
        }
    }
}