package com.michael.appverse.features.todo.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.michael.appverse.commons.ui.toast
import com.michael.appverse.core.presentation.MainActivity
import com.michael.appverse.databinding.FragmentAddTodoBinding
import com.michael.appverse.features.todo.model.TodoData
import com.michael.appverse.features.todo.presentation.viewModel.TodoSharedViewModel
import com.michael.appverse.features.todo.presentation.viewModel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTodoFragment : Fragment() {

    private var binding: FragmentAddTodoBinding? = null
    private val viewModel: TodoViewModel by viewModels()
    private val sharedViewModel: TodoSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Add Todo"

        binding!!.toDoAddPrioritySpinner.onItemSelectedListener = sharedViewModel.listener

        binding!!.addNoteFab.setOnClickListener {

            val title = binding!!.toToAddTitle.text.toString()
            val description = binding!!.toDoAddEditText.text.toString()
            val priority = binding!!.toDoAddPrioritySpinner.selectedItem.toString()

            if (sharedViewModel.verifyInputs(title, description)) {
                val todo = TodoData(0, title, description, false, sharedViewModel.parsePriority(priority))
                viewModel.addToDB(todo).also {
                    toast(requireContext(), "Successfully added todo")
                    findNavController().navigateUpOrFinish(requireActivity())
                    // hide keyboard
                    (activity as MainActivity).hideKeyboard()
                }
            } else {
                toast(requireContext(), "Please fill all the fields")
            }
        }

//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//            }
//        })

    }


    private fun NavController.navigateUpOrFinish(activity: FragmentActivity): Boolean {
        return if (navigateUp()) {
            true
        } else {
            activity.finish()
            true
        }
    }
}