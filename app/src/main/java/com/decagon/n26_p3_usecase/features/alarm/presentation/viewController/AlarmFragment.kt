package com.decagon.n26_p3_usecase.features.alarm.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.decagon.n26_p3_usecase.databinding.FragmentAlarmBinding
import com.decagon.n26_p3_usecase.features.alarm.presentation.viewModel.AlarmViewModel
import com.decagon.n26_p3_usecase.features.alarm.service.AlarmService
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AlarmFragment : Fragment() {

    private var binding: FragmentAlarmBinding? = null
    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(layoutInflater)
        return binding?.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.button.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val popTime = PopTime()
            popTime.show(fragmentManager!!, "timePicker")
        }
    }

    override fun onResume() {
        super.onResume()
        AlarmService.setAlarm(requireContext(), viewModel)
        viewModel.data.observeForever { binding!!.alarmTime.text = it }
    }

    override fun onPause() {
        super.onPause()
        viewModel.data.removeObservers(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}