package com.michael.appverse.commons.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import androidx.annotation.RequiresApi


object ViewToBitmap {

        @RequiresApi(Build.VERSION_CODES.O)
        fun getScreenShotFromView(view: View, activity: Activity, listener: PixelCopyListener) {
            activity.window?.let { window ->
                val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                val locationOfViewInWindow = IntArray(2)
                view.getLocationInWindow(locationOfViewInWindow)
                try {
                    PixelCopy.request(
                        window,
                        Rect(
                            locationOfViewInWindow[0],
                            locationOfViewInWindow[1],
                            locationOfViewInWindow[0] + view.width,
                            locationOfViewInWindow[1] + view.height
                        ), bitmap, { copyResult ->
                            if (copyResult == PixelCopy.SUCCESS) {
                                listener.onCopySuccess(bitmap)
                            } else {
                                listener.onCopyError()
                            }
                            // possible to handle other result codes ...
                        },
                        Handler(Looper.getMainLooper())
                    )
                } catch (e: IllegalArgumentException) {
                    // PixelCopy may throw IllegalArgumentException, make sure to handle it
                    e.printStackTrace()
                }
            }
        }
        //deprecated version
        /*  Method which will return Bitmap after taking screenshot. We have to pass the view which we want to take screenshot.  */
        fun getScreenShot(view: View): Bitmap {
            val screenView = view.rootView
            screenView.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(screenView.drawingCache)
            screenView.isDrawingCacheEnabled = false
            return bitmap
        }
}