package com.decagon.n26_p3_usecase.commons.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener

class GetPermission {

    fun initialize(permission: String, context: Context, message: String) {

        Dexter.withContext(context)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                    DialogOnDeniedPermissionListener.Builder
                        .withContext(context)
                        .withTitle("Permission")
                        .withMessage(message)
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
