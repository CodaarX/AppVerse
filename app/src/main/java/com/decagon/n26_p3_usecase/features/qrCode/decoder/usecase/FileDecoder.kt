package com.decagon.n26_p3_usecase.features.qrCode.decoder.usecase

import com.decagon.n26_p3_usecase.features.qrCode.ClassConverter
import com.decagon.n26_p3_usecase.features.qrCode.GsonCleaner
import com.decagon.n26_p3_usecase.features.qrCode.decoder.decoderContract.BarCodeDecoder
import com.decagon.n26_p3_usecase.features.qrCode.model.QRDetails


class FileDecoder (val data: String) : BarCodeDecoder {

    override fun decodeBarCode() : QRDetails {
        // convert from barcode matrix to Json object - TO DO
        return ClassConverter.toClass(data, QRDetails::class.java.newInstance())
    }
}