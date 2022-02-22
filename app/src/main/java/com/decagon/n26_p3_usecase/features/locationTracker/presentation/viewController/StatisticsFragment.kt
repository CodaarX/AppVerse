package com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel.MainViewModel
import com.decagon.n26_p3_usecase.features.locationTracker.presentation.viewModel.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private val  viewModel: StatisticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }
}