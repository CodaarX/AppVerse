package com.decagon.n26_p3_usecase.features.qrCode.generator.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.hideView
import com.decagon.n26_p3_usecase.commons.utils.log
import com.decagon.n26_p3_usecase.commons.utils.showView
import com.decagon.n26_p3_usecase.commons.utils.toast
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentQRGeneratorBinding
import com.decagon.n26_p3_usecase.features.qrCode.BitMapConverter
import com.decagon.n26_p3_usecase.features.qrCode.generator.usecases.ZxingCodeGenerator
import com.decagon.n26_p3_usecase.features.qrCode.model.QRDetails

class QRGeneratorFragment : BaseFragment() {

    private lateinit var binding : FragmentQRGeneratorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQRGeneratorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val code = sharedPreference.loadFromSharedPref("qrCode")

        if(code.isEmpty()) {
            // display input fields
            binding.barcodeImageView.hideView()
            binding.editButton.hideView()

            binding.generateButton.setOnClickListener {
                with(binding){
                    val fullName = nameInputField.text.toString().trim()
                    val phoneNumber = contactInputField.text.toString().trim()
                    val email = emailInputField.text.toString().trim()
                    val address = addressInputField.text.toString().trim()
                    val linkedIn = linkedInInputField.text.toString().trim()
                    val profession = professionInputField.text.toString().trim()

                    if(fullName.isNotEmpty() && phoneNumber.isNotEmpty() && email.isNotEmpty()){
                        val details = QRDetails(fullName, phoneNumber, email, address, profession, linkedIn)
                        val generator = ZxingCodeGenerator(details).generateBarCode()
                        barcodeImageView.setImageBitmap(generator)
                        binding.enterDetailsRootView.hideView()
                        binding.barcodeImageView.showView()
                        sharedPreference.saveToSharedPref("qrCode", BitMapConverter.toString(generator))
                    } else {
                        toast(requireContext(), "Please fill in your name, email and phone number at least")
                    }
                }
            }

        } else {
            // display image
            binding.enterDetailsRootView.hideView()
            val string = sharedPreference.loadFromSharedPref("qrCode")
            binding.barcodeImageView.setImageBitmap(BitMapConverter.toBitmap(string))
            binding.barcodeImageView.showView()
            binding.editButton.showView()

        }

    }
}