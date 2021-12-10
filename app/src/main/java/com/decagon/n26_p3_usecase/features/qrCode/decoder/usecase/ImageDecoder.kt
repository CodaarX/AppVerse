package com.decagon.n26_p3_usecase.features.qrCode.decoder.usecase

import com.decagon.n26_p3_usecase.features.qrCode.decoder.decoderContract.BarCodeDecoder
import com.decagon.n26_p3_usecase.features.qrCode.model.QRDetails
import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.common.HybridBinarizer
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.media.Image


class ImageDecoder   {
//    : BarCodeDecoder


//    override fun decodeBarCode(data: String) {
//
//        File root = Environment.getExternalStorageDirectory();
//        ImageView IV = (ImageView) findViewById(R.id."image view");
//        Bitmap bMap = BitmapFactory.decodeFile(root+"/images/01.jpg");
//        IV.setImageBitmap(bMap);

//        val image: Image = reader.acquireLatestImage()
//        val buffer: ByteBuffer = image.planes[0].buffer
//        val bytes = ByteArray(buffer.capacity())
//        buffer.get(bytes)
//        val bitmapImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)

//    }



}