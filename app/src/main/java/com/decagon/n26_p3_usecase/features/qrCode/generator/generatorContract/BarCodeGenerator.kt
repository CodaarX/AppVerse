package com.decagon.n26_p3_usecase.features.qrCode.generator.generatorContract

import android.graphics.Bitmap

interface BarCodeGenerator {
    fun generateBarCode() : Bitmap
}