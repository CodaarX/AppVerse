package com.decagon.n26_p3_usecase.features.qrCode.decoder.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentQRReaderBinding
import com.opensooq.supernova.gligar.GligarPicker
import android.os.Build
import androidx.annotation.RequiresApi
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.hideView
import com.decagon.n26_p3_usecase.commons.ui.log
import com.decagon.n26_p3_usecase.commons.ui.showView
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.features.qrCode.utils.QRImageDecoder
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import me.dm7.barcodescanner.zxing.ZXingScannerView

import com.google.zxing.*


class QRReaderFragment : BaseFragment(), ZXingScannerView.ResultHandler {

    private val PICKER_REQUEST_CODE = 100
    private lateinit var binding : FragmentQRReaderBinding
    lateinit var  qrCodeScanner : ZXingScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQRReaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qrCodeScanner =  binding.qrCodeScanner
        binding.decode.hideView()
    }

    override fun onResume() {
        super.onResume()

        binding.gallery.setOnClickListener {
            binding.camera.hideView()
            GligarPicker().requestCode(PICKER_REQUEST_CODE).withFragment(this).show()
            binding.gallery.hideView()
        }

        binding.camera.setOnClickListener {
            binding.barcodeImageView.hideView()
            binding.gallery.hideView()

            setScannerProperties()

            Dexter.withContext(context)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    @RequiresApi(Build.VERSION_CODES.R)
                    override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                        qrCodeScanner.startCamera()
                        qrCodeScanner.setResultHandler(this@QRReaderFragment)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                        DialogOnDeniedPermissionListener.Builder
                            .withContext(context)
                            .withTitle("Storage permission")
                            .withMessage("Camera permission is needed to use this application")
                            .withButtonText(android.R.string.ok)
                            .withIcon(R.drawable.ic_baseline_message_24)
                            .build()

                        toast(requireContext(), "Permission is required to use this feature")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                val imagesList =
                    data?.extras?.getStringArray(GligarPicker.IMAGES_RESULT)// return list of selected images paths.
                if (!imagesList.isNullOrEmpty()) {
                    Glide
                        .with(requireContext())
                        .load(imagesList[0])
                        .centerCrop()
                        .into(binding.barcodeImageView);

                    binding.decode.showView()

                    binding.decode.setOnClickListener {

                        val bitMapDrawable: BitmapDrawable = binding.barcodeImageView.drawable as BitmapDrawable
                        val bitmap = bitMapDrawable.bitmap

                        QRImageDecoder().readImage(bitmap, requireContext())

                    }
                }
            }
            else -> {
                log("sth went wrong again")
            }
        }
    }

    private fun setScannerProperties() {

        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)
//        if (Build.MANUFACTURER.equals(HUAWEI, ignoreCase = true))
//            qrCodeScanner.setAspectTolerance(0.5f)
    }

    override fun handleResult(p0: Result?) {
        log(p0)
        qrCodeScanner.stopCamera()
    }
}