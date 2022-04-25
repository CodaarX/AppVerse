package com.michael.appverse.features.qrCode.utils

import android.graphics.Bitmap
import android.graphics.Color
import com.michael.appverse.commons.ui.log
import com.michael.appverse.features.qrCode.encoder.generatorContract.BarCodeGenerator
import com.michael.appverse.features.qrCode.model.QRDetails
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix

class ZxingCodeGenerator (val data : QRDetails) : BarCodeGenerator {

    lateinit var matrix : BitMatrix
    lateinit var result : Bitmap

    override fun generateBarCode() : Bitmap = convertToBitmap(data)

    private fun convertToBitmap(data: QRDetails) : Bitmap {
        try {
            val info = ClassConverter.toJson(data)
            matrix = MultiFormatWriter().encode(info , BarcodeFormat.QR_CODE, 300, 300)
        } catch (e: WriterException) {
            log(e)
            e.printStackTrace()
        }
        return encodeBitmap(matrix)
    }

    private fun encodeBitmap(matrix: BitMatrix) : Bitmap {
        val width = matrix.width
        val height = matrix.height

        val num = width * height

        val pixels : IntArray = (0..num).toList().toIntArray()

        for(x in 0 until height){
            val offset = x * width
            for(y in 0 until width) {
                if(matrix.get(x,y))  pixels[offset + y] = Color.BLACK else Color.WHITE
            }
        }

        result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        result.setPixels(pixels, 0, width, 0, 0, width, height)

        return result
    }

}
