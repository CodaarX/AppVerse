package com.decagon.n26_p3_usecase.commons.utils

import android.graphics.Bitmap

interface PixelCopyListener {
    fun onCopySuccess(bitmap: Bitmap)
    fun onCopyError()
}
