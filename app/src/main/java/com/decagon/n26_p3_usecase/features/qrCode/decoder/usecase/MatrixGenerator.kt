package com.decagon.n26_p3_usecase.features.qrCode.decoder.usecase

import com.decagon.n26_p3_usecase.features.qrCode.ClassConverter
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class  MatrixGenerator<T>(val data : T) {

    lateinit var matrix : BitMatrix

    fun generateMatrix() : BitMatrix {
        val info = ClassConverter.toJson(data)
        matrix = MultiFormatWriter().encode(info , BarcodeFormat.QR_CODE, 300, 300)
        return matrix
    }

}