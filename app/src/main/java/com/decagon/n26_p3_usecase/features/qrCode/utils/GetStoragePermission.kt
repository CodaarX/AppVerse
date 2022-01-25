package com.decagon.n26_p3_usecase.features.qrCode.utils

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener

class GetStoragePermission() {

    fun checkPermission(data: Bitmap? = null, context: Context) {

        Dexter.withContext(context)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                    data?.let {
                        SaveImage.withContentResolver(data, context)
                    }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    DialogOnDeniedPermissionListener.Builder
                        .withContext(context)
                        .withTitle("Storage permission")
                        .withMessage("Storage permission is needed to use this application")
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.drawable.ic_baseline_message_24)
                        .build()

                    toast(context, "Permission is required to use this feature")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) { /* ... */
                    token.continuePermissionRequest()
                }
            }).check()

    }

}