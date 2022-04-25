package com.michael.appverse.commons.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.util.DisplayMetrics
import android.view.View
import java.io.File
import java.io.FileOutputStream


object CardViewToImageUtil {
    fun loadView( cardView: View, displayMetrics: DisplayMetrics) {
        try {
            cardView.isDrawingCacheEnabled = true
            val bitmap: Bitmap = loadBitmapFromView(cardView, displayMetrics)
            cardView.isDrawingCacheEnabled = false
            val mPath = Environment.getExternalStorageDirectory().toString() + "/sid.jpg"
            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }


    private fun loadBitmapFromView(v: View, displayMetrics: DisplayMetrics): Bitmap {

        v.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY)
        )
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
        val returnedBitmap = Bitmap.createBitmap(
            v.measuredWidth,
            v.measuredHeight, Bitmap.Config.ARGB_8888
        )
        val c = Canvas(returnedBitmap)
        v.draw(c)
        return returnedBitmap
    }
}