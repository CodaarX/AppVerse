package com.michael.appverse.features.qrCode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michael.appverse.R


class QRCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)
    }


    override fun onDestroy() {
        super.onDestroy()
        this.finish()
    }

}