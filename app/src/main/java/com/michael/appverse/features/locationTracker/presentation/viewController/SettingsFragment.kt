package com.michael.appverse.features.locationTracker.presentation.viewController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.michael.appverse.R
import com.michael.appverse.commons.ui.toast
import com.michael.appverse.core.baseClasses.BaseFragment
import com.michael.appverse.core.presentation.MainActivity
import com.michael.appverse.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Settings"

        val name = sharedPreference.loadFromSharedPref<String>("String", "runner_name")
        val float = sharedPreference.loadFromSharedPref<Float>("Float", "runner_weight")

        binding.etName.setText(name)
        binding.etWeight.setText(float.toString())

        binding.btnApplyChanges.setOnClickListener {
            saveDataToSharedPreference(binding.etName.text.toString(), binding.etWeight.text.toString())
        }
        binding.statisticsLayout.setOnClickListener {
            findNavController().navigate(R.id.statisticsFragment)
        }

        binding.runsLayout.setOnClickListener {
            findNavController().navigate(R.id.runFragment)
        }
    }

    private fun saveDataToSharedPreference(name: String, weight: String){

        if (name.isEmpty() || weight.isEmpty()) {
            toast( requireContext(),"Please fill all the fields")
        } else {
            sharedPreference.saveToSharedPref("runner_name", name)
            sharedPreference.saveToSharedPref("runner_weight", weight.toFloat())
            findNavController().navigate(R.id.runFragment)
        }

    }

}