package com.decagon.n26_p3_usecase.features.todo.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.hideView
import com.decagon.n26_p3_usecase.commons.utils.showView
import com.decagon.n26_p3_usecase.core.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Todo List"

        binding.addNoteFab.setOnClickListener { findNavController().navigate(R.id.action_listFragment_to_addTodoFragment) }

        binding.fragmentList.setOnClickListener { findNavController().navigate(R.id.action_listFragment_to_updateFragment) }

        binding.search.setOnClickListener { binding.searchBar.showView() }

        binding.searchBar.hideView()
    }

}