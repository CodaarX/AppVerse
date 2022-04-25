package com.michael.appverse.features.qrCode

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.michael.appverse.R
import com.michael.appverse.databinding.FragmentQROptionsBinding

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

//        verifyExit()
    }

    private fun verifyExit() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    android.app.AlertDialog.Builder(requireContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exit App")
                        .setMessage("Are you sure you want to exit this application?")
                        .setPositiveButton("Yes",
                            DialogInterface.OnClickListener { dialog, which -> requireActivity().finish() })
                        .setNegativeButton("No", null)
                        .show()
                }
            })
    }


}