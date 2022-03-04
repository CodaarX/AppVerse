package com.decagon.n26_p3_usecase.features.qrCode.utils

import com.google.zxing.common.HybridBinarizer

import android.content.Context
import android.graphics.Bitmap
import com.decagon.n26_p3_usecase.commons.ui.log
import com.google.zxing.*


class QRImageDecoder {

    fun readImage(selectedImage: Bitmap, context: Context){

        try {
//            var contents: String? = null
            val intArray = IntArray(selectedImage.width * selectedImage.height)
            selectedImage.getPixels(
                intArray,
                0,
                selectedImage.width,
                0,
                0,
                selectedImage.width,
                selectedImage.height
            )
            val source: LuminanceSource = RGBLuminanceSource(
                selectedImage.width,
                selectedImage.height,
                intArray
            )
            val bitmap = BinaryBitmap(HybridBinarizer(source))
            val reader: Reader = MultiFormatReader()

            val result = reader.decode(bitmap)
//            contents = result.text
            log(result)

        } catch (e: Exception) {
            e.printStackTrace()
            log(e)
        }
    }
}