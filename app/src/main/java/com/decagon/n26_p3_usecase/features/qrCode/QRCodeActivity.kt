package com.decagon.n26_p3_usecase.features.qrCode

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagon.n26_p3_usecase.R


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