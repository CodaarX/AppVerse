package com.decagon.n26_p3_usecase.features.qrCode.reader.decoderContract

import android.graphics.Bitmap

interface BarCodeDecoder {
    fun decodeBarCode(data: String)
}