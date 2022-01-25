package com.decagon.n26_p3_usecase.features.qrCode.utils

import android.R.attr
import android.widget.Toast

import com.google.zxing.common.HybridBinarizer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.decagon.n26_p3_usecase.commons.utils.log
import com.decagon.n26_p3_usecase.commons.utils.toast
import com.google.zxing.*
import java.io.FileNotFoundException
import java.io.InputStream
import kotlin.Result


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