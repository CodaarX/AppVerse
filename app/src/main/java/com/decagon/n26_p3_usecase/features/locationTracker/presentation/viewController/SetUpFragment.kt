package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.hideView
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentSetUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetUpFragment : BaseFragment() {

   lateinit var binding : FragmentSetUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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


        binding.startButon.setOnClickListener {
            sharedPreference.saveToSharedPref("runner_set", true)
           findNavController().navigate(R.id.run_tracker_navigator)

        }
    }

}