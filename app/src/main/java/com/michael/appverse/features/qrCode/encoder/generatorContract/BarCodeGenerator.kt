package com.michael.appverse.features.qrCode.encoder.generatorContract

import android.graphics.Bitmap

interface BarCodeGenerator {
    fun generateBarCode() : Bitmap
}