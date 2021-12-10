package com.decagon.n26_p3_usecase.features.qrCode.encoder.generatorContract

import android.graphics.Bitmap
import com.google.zxing.common.BitMatrix

interface BarCodeGenerator {
    fun generateBarCode() : Bitmap
}