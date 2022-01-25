package com.decagon.n26_p3_usecase.features.alarm.presentation.viewController

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.features.alarm.presentation.viewModel.AlarmViewModel
import com.decagon.n26_p3_usecase.features.alarm.service.AlarmService.setAlarm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopTime : DialogFragment(){

    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? { return inflater.inflate(R.layout.pop_time, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val setTimeButton = view.findViewById<View>(R.id.setTime) as Button
        val timePicker = view.findViewById<View>(R.id.time_picker) as TimePicker

        setTimeButton.setOnClickListener {
            viewModel.setData("${timePicker.hour}: ${timePicker.minute}")
            viewModel.saveData(timePicker.hour, timePicker.minute)
            setAlarm(requireContext(), viewModel)
            dismiss()
        }
    }

}