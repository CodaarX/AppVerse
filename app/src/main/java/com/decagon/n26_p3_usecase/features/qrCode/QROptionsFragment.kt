package com.decagon.n26_p3_usecase.features.qrCode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.databinding.FragmentQROptionsBinding
import com.decagon.n26_p3_usecase.databinding.FragmentQRReaderBinding

class QROptionsFragment : Fragment() {

    private lateinit var binding : FragmentQROptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQROptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.generateCode.setOnClickListener {
            findNavController().navigate(R.id.QRGeneratorFragment)
        }

        binding.readCode.setOnClickListener {
            findNavController().navigate(R.id.QRReaderFragment)
        }

    }

}