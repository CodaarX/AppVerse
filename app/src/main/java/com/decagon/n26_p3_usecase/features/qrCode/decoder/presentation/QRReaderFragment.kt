package com.decagon.n26_p3_usecase.features.qrCode.decoder.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.log
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.features.qrCode.BitMapConverter
import com.decagon.n26_p3_usecase.features.qrCode.ClassConverter
import com.decagon.n26_p3_usecase.features.qrCode.decoder.usecase.FileDecoder
import com.decagon.n26_p3_usecase.features.qrCode.model.QRDetails



class QRReaderFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_reader, container, false)
    }

    override fun onResume() {
        super.onResume()

        val code = sharedPreference.loadFromSharedPref("qrCode")

        if (code.isNotEmpty()) {
            val data = BitMapConverter.toBitmap(code)
            // here we can read the data from the bitmap by parsing through it
            // we can also decide to read the data from the matrix itself

                //FileDecoder(code).decodeBarCode()

//            log(data)

        }
    }
}