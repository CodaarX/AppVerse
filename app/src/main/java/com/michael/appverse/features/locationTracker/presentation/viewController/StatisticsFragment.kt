package com.michael.appverse.features.locationTracker.presentation.viewController

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.michael.appverse.core.presentation.MainActivity
import com.michael.appverse.databinding.FragmentStatisticsBinding
import com.michael.appverse.features.locationTracker.presentation.viewModel.StatisticsViewModel
import com.michael.appverse.features.locationTracker.utils.TrackingUtils
import com.github.mikephil.charting.components.XAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private val  viewModel: StatisticsViewModel by viewModels()
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Run Stats"

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner) {
            it?.let {
                val totalTimeRun = TrackingUtils.getFormattedStopWatchTime(it)
                binding.tvTotalTime.text = totalTimeRun
            }
        }

        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000f
                val totalDistance = round(km * 10f) / 10f
                val totalDistanceString = "$totalDistance km"
                binding.tvTotalDistance.text = totalDistanceString
            }
        }

        viewModel.totalAvgSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val totalAvgSpeed = round(it * 10f) / 10f
                val totalAvgSpeedString = "$totalAvgSpeed km/h"
                binding.tvAverageSpeed.text = totalAvgSpeedString
            }
        }

        viewModel.totalCalories.observe(viewLifecycleOwner) {
            it?.let {
                val totalCaloriesBurned = "$it kcal"
                binding.tvTotalCalories.text = totalCaloriesBurned
            }
        }
    }

    private fun setUpBarChart() {
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }

        binding.barChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }

        binding.barChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }

        binding.barChart.apply {
            description.text = "Avg Speed over Time"
            description.isEnabled = false
            legend.isEnabled = false
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
        }
    }

}