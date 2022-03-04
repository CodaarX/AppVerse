package com.decagon.n26_p3_usecase.features.qrCode.utils

import com.decagon.n26_p3_usecase.commons.ui.log
import com.decagon.n26_p3_usecase.features.qrCode.model.QRDetails
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class  MatrixGenerator<T>(val data : T) {

    lateinit var matrix : BitMatrix
    private val jsonString = ClassConverter.toJson(data)


    fun generateMatrix() : BitMatrix {
        matrix = MultiFormatWriter().encode(jsonString , BarcodeFormat.QR_CODE, 300, 300)
        return matrix
    }

//    fun decodeMatrix
    fun decodeMatrix() {
    log(jsonString.toByteArray().size)
        when (data) {
            is QRDetails -> {
                val data = ClassConverter.toClass<QRDetails>(jsonString)
                log(data)

//                val decoded = MultiFormatReader().decode(matrix)

            }
        }
    }

}