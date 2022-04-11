package com.decagon.n26_p3_usecase.features.todo.presentation.viewController

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.hideView
import com.decagon.n26_p3_usecase.commons.ui.showView
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.commons.utils.GenericDialogueBuilder
import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentListBinding
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import com.decagon.n26_p3_usecase.features.todo.presentation.adapter.SwipeToDeleteLeft
import com.decagon.n26_p3_usecase.features.todo.presentation.adapter.TodoListAdapter
import com.decagon.n26_p3_usecase.features.todo.presentation.viewModel.TodoSharedViewModel
import com.decagon.n26_p3_usecase.features.todo.presentation.viewModel.TodoViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.LandingAnimator
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    private val adapter: TodoListAdapter by lazy { TodoListAdapter() }
    private val todoViewModel: TodoViewModel by viewModels()
    private val sharedViewModel: TodoSharedViewModel by viewModels()
    private lateinit var binding: FragmentListBinding

    override fun onStart() {
        super.onStart()
        todoViewModel.getAllTodo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Todo List"

        setUpRecyclerView()
        observeLiveData()
        setOnClickListeners()

//        verifyExit()
    }

    private fun setOnClickListeners() {
        binding.searchBottomLayout.setOnClickListener {
            if (binding.searchBar.isVisible) {
                binding.searchBar.hideView()
            } else {
                binding.searchBar.showView()
            }

            binding.etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (s.isNotEmpty()) {
                        searchTodo(s.toString())
                    }
                }
            })
        }

        binding.searchBar.hideView()

        binding.clearListLayout.setOnClickListener {
            if (todoViewModel.todoList.value!!.isNotEmpty()) {
                confirmRemoval()
            } else {
                toast(requireContext(), "No data to delete")
            }
        }

        binding.addNoteFab.setOnClickListener { findNavController().navigate(R.id.action_listFragment_to_addTodoFragment) }

        binding.sortList.setOnClickListener {
            if (todoViewModel.todoList.value!!.isNotEmpty()) {
                inflateBottomSheet()
            } else {
                toast(requireContext(), "No data to sort")
            }
        }
    }

    private fun observeLiveData() {
        todoViewModel.todoList.observe(viewLifecycleOwner) {

            when (it.isNotEmpty()) {
                true -> {
                    binding.noDataImageView.hideView()
                    binding.noDataTextView.hideView()
                }
                false -> {
                    binding.noDataImageView.showView()
                    binding.noDataTextView.showView()
                }
            }
            adapter.setTodos(it)
        }
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.toToRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 300
        }

        recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.addNoteFab.hideView()
            } else {
                binding.addNoteFab.showView()
            }
        }
        swipeToDeleteLeft(recyclerView)
    }

    private fun swipeToDeleteLeft(recyclerView: RecyclerView) {
        val swipeHandler = object : SwipeToDeleteLeft() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.todos[viewHolder.adapterPosition]
                todoViewModel.deleteTodo(itemToDelete)
                toast(requireContext(), "Successfully deleted ${itemToDelete.title}")

                // restore the item
                restoreDeletedTodo(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }

            override fun onItemSwiped(position: Int) {
                adapter.notifyDataSetChanged()
                adapter.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedTodo(view: View, deletedItem: TodoData, position: Int) {
        val snackbar = Snackbar.make(view, "Undo Delete", Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO") {
            todoViewModel.addToDB(deletedItem)
            adapter.notifyItemInserted(position)
        }
        snackbar.show()
    }

    private fun searchTodo(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"
        todoViewModel.searchTodo(searchQuery)
        observeLiveData()
    }

    private fun inflateBottomSheet() {

        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)

        val bottomSheetView: View = layoutInflater.inflate(
            R.layout.sort_bottom_sheet,
            view?.findViewById(R.id.sort_bottom_sheet_root_layout) as LinearLayout?
        )

        val highPriorityButton: RadioButton? = bottomSheetView.findViewById(R.id.high_priority_radio_button)

        val lowPriorityButton: RadioButton? = bottomSheetView.findViewById(R.id.low_priority_radio_button)

        lowPriorityButton?.setOnClickListener {
            if (sharedPreference.loadFromSharedPref<Boolean>("Boolean", "sortedByDesc")) {
                todoViewModel.sortByLowPriority.observe(viewLifecycleOwner) { adapter.setTodos(it) }
                sharedPreference.saveToSharedPref("sortedByDesc", false)
            } else {
                toast(requireContext(), "Already sorted by LOW Priority")
            }

            dialog.dismiss()
        }

        highPriorityButton?.setOnClickListener {
            if (sharedPreference.loadFromSharedPref<Boolean>("Boolean", "sortedByDesc")) {
                toast(requireContext(), "Already sorted by HIGH Priority")
            } else {
                todoViewModel.sortByHighPriority.observe(viewLifecycleOwner) { adapter.setTodos(it) }
                sharedPreference.saveToSharedPref("sortedByDesc", true)
            }
            dialog.dismiss()
        }

        dialog.setContentView(bottomSheetView)
        dialog.show()
    }

    private fun confirmRemoval() {
        GenericDialogueBuilder.showDialogue(
            requireContext(), R.style.Theme_AppCompat_Dialog_Alert,
            "Are you sure you want to delete all the todos?",
            "Delete All",
            R.drawable.ic_delete,
            "Delete All",
            "Cancel",
            { todoViewModel.deleteAllTodo() },
            { toast(requireContext(), "Cancelled") }
        )
    }

    fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    override fun onStop() {
        super.onStop()
        sharedPreference.removeSharedPref("sortedByDesc")
    }

    private fun verifyExit() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    GenericDialogueBuilder.showDialogue(
                        requireContext(), R.style.Theme_AppCompat_Dialog_Alert,
                        "Are you sure you want to exit?",
                        "Exit",
                        R.drawable.ic_baseline_logout_24,
                        "Exit",
                        "Cancel",
                        { activity?.finish() },
                        { toast(requireContext(), "Cancelled") }
                    )
                }
            }
        )
    }
}
