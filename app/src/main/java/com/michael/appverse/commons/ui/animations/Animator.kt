package com.michael.appverse.commons.ui.animations

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.michael.appverse.R

object Animator {

    fun animateActivityZoom(context: Context) {
        (context as Activity).overridePendingTransition(
            R.anim.animate_zoom_enter,
            R.anim.animate_zoom_exit
        )
    }

    fun animateActivityFadeContext(context: Context) {
        (context as Activity).overridePendingTransition(
            R.anim.animate_fade_enter,
            R.anim.animate_fade_exit
        )
    }


    fun animateFragmentZoom(context: Context) {
        (context as FragmentActivity).overridePendingTransition(
            R.anim.animate_zoom_enter,
            R.anim.animate_zoom_exit
        )
    }

    fun animateFragmentFadeContext(context: Context) {
        (context as FragmentActivity).overridePendingTransition(
            R.anim.animate_fade_enter,
            R.anim.animate_fade_exit
        )
    }


}