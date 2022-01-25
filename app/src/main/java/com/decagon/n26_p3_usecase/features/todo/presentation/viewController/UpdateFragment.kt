package com.decagon.n26_p3_usecase.features.todo.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.core.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentUpdateBinding
import com.decagon.n26_p3_usecase.features.todo.presentation.viewModel.TodoSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private var binding: FragmentUpdateBinding? = null
    private val sharedViewModel: TodoSharedViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Update Item"

        binding!!.toDoAddPrioritySpinner.onItemSelectedListener = sharedViewModel.listener

    }
}