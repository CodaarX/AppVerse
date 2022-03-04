package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentSetUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetUpFragment : BaseFragment() {

   lateinit var binding : FragmentSetUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setTitle("Tracker Set up")

        binding.startButon.setOnClickListener {
           saveDataToSharedPreference()
        }
    }

    private fun saveDataToSharedPreference(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()

        if (name.isEmpty() || weight.isEmpty()) {
            toast( requireContext(),"Please fill all the fields")
            return false
        } else {
            sharedPreference.saveToSharedPref("runner_name", name)
            sharedPreference.saveToSharedPref("runner_weight", weight.toFloat())
            sharedPreference.saveToSharedPref("runner_set", true)
            setTitle("Let's go, $name!")
            findNavController().navigate(R.id.run_tracker_navigator)
            return true
        }

    }

    private fun setTitle(title: String) {
        (activity as MainActivity).supportActionBar?.title = title
    }
}