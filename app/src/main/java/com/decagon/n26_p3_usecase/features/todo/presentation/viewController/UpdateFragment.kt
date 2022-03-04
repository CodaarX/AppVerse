package com.decagon.n26_p3_usecase.features.todo.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentUpdateBinding
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import com.decagon.n26_p3_usecase.features.todo.presentation.viewModel.TodoSharedViewModel
import com.decagon.n26_p3_usecase.features.todo.presentation.viewModel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private var binding: FragmentUpdateBinding? = null
    private val sharedViewModel: TodoSharedViewModel by activityViewModels()
    private val todoViewModel: TodoViewModel by activityViewModels()
    private val args by navArgs<UpdateFragmentArgs>()

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

        args.let {
            it.currentItem.let { it ->
                binding?.toToAddTitle?.setText(it.title)
                binding?.toDoAddEditText?.setText(it.description)
                binding?.toDoAddPrioritySpinner?.setSelection(sharedViewModel.parsePrioritytoInt(it.priority))
            }
        }
        binding!!.toDoAddPrioritySpinner.onItemSelectedListener = sharedViewModel.listener

        binding!!.saveNoteFab.setOnClickListener { updateItem() }

        binding!!.deleteItemLayout.setOnClickListener { confirmItemRemoval() }
    }

    private fun updateItem() {
        val title = binding?.toToAddTitle?.text.toString()
        val description = binding?.toDoAddEditText?.text.toString()
        val priority = binding?.toDoAddPrioritySpinner?.selectedItem.toString()

        val validation = sharedViewModel.verifyInputs(title, description)

        if (validation) {

            todoViewModel.updateTodo(
                TodoData(
                    args.currentItem.id,
                    title,
                    description,
                    false,
                    sharedViewModel.parsePriority(priority)
                )
            )
            toast(requireContext(), "Successfully updated")
            requireActivity().onBackPressed()
        } else {
            toast(requireContext(), "Please fill all fields")
        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${binding?.toToAddTitle?.text}'")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setPositiveButton("Yes") { _, _ ->
            todoViewModel.deleteTodo(args.currentItem)
            toast(requireContext(), "Successfully removed '${args.currentItem.title}'")
            requireActivity().onBackPressed()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
            toast(requireContext(), "Item not deleted")
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}