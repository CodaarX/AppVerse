package com.michael.appverse.features.qrCode.encoder.presentation

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.michael.appverse.R
import com.michael.appverse.commons.ui.hideView
import com.michael.appverse.commons.ui.showView
import com.michael.appverse.core.baseClasses.BaseFragment
import com.michael.appverse.databinding.FragmentQRGeneratorBinding
import com.michael.appverse.features.qrCode.model.QRDetails
import com.michael.appverse.features.qrCode.utils.*
import com.michael.appverse.features.qrCode.utils.ClassConverter.gson

class QRGeneratorFragment : BaseFragment() {

    private lateinit var binding: FragmentQRGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference.clearSharedPref()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQRGeneratorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val code = sharedPreference.loadFromSharedPref<String>("String", "qrCode")

        if (code.isEmpty()) {
            // display input fields
            binding.barcodeImageView.hideView()
            binding.editButton.hideView()

            binding.generateButton.setOnClickListener {
                with(binding) {
                    val fullName = nameInputField.text.toString().trim()
                    val phoneNumber = contactInputField.text.toString().trim()
                    val email = emailInputField.text.toString().trim()
                    val address = addressInputField.text.toString().trim()
                    val linkedIn = linkedInInputField.text.toString().trim()
                    val profession = professionInputField.text.toString().trim()

                    val details = QRDetails(fullName, phoneNumber, email, address, profession, linkedIn)

                    // generator is a bitmap
                    val bitmap = QRCodeHelper
                        .newInstance(requireContext())
                        ?.setContent(gson.toJson(details))
                        ?.setErrorCorrectionLevel(ErrorCorrectionLevel.Q)
                        ?.setMargin(2)
                        ?.qRCOde

//                    val bitmap = ZxingCodeGenerator(details).generateBarCode()

                    barcodeImageView.setImageBitmap(bitmap)
                    binding.enterDetailsRootView.hideView()
                    binding.barcodeImageView.showView()
                    binding.editButton.showView()

                    // convert bit map to string to save to shared pref
                    bitmap?.let { it1 -> BitMapConverter.toString(it1) }?.let { it2 ->
                        sharedPreference.saveToSharedPref("qrCode", it2)
                    }
                    binding.editButton.setOnClickListener {
                        binding.enterDetailsRootView.showView()
                        binding.barcodeImageView.hideView()
                        binding.editButton.hideView()
                    }

//                    } else {
//                        toast(requireContext(), "Please fill in your name, email and phone number at least")
//                    }
                }
            }
        } else {
            // display image
            binding.enterDetailsRootView.hideView()
            val string = sharedPreference.loadFromSharedPref<String>("String", "qrCode")
            binding.barcodeImageView.setImageBitmap(BitMapConverter.toBitmap(string))
            binding.barcodeImageView.showView()
            binding.editButton.showView()
            binding.editButton.setOnClickListener { findNavController().navigate(R.id.QRReaderFragment) }
        }

        binding.saveButton.setOnClickListener {
            val bitMapDrawable: BitmapDrawable = binding.barcodeImageView.drawable as BitmapDrawable
            val bitmap = bitMapDrawable.bitmap
            GetStoragePermission().checkPermission(bitmap, requireContext())
        }
    }
}
