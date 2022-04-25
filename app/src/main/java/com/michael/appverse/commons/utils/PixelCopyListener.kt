package com.michael.appverse.commons.utils

import android.graphics.Bitmap

interface PixelCopyListener {
    fun onCopySuccess(bitmap: Bitmap)
    fun onCopyError()
}
