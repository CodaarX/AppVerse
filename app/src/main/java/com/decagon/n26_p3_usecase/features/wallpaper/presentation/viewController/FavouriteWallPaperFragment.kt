package com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewController

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.hideView
import com.decagon.n26_p3_usecase.commons.ui.showView
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.commons.utils.NetworkLiveData
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentFavouriteWallPaperBinding
import com.decagon.n26_p3_usecase.databinding.FragmentWallPaperBinding
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import com.decagon.n26_p3_usecase.features.todo.presentation.adapter.SwipeToDeleteLeft
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.adapter.FavouriteAdapter
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.adapter.WallPaperAdapter
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewModel.WallPaperViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteWallPaperFragment : BaseFragment() {

    private lateinit var binding: FragmentFavouriteWallPaperBinding
    val viewModel: WallPaperViewModel by activityViewModels()
    private val adapter: FavouriteAdapter by lazy { FavouriteAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteWallPaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            binding.progressBar.showView()
            binding.recyclerView.hideView()
            delay(3000)
        }
        viewModel.getWallPaperFromDatabase()
        binding.recyclerView.showView()
        setUpObserver()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))
    }

    private fun setUpObserver(){
        viewModel.favWallPapers.observe(viewLifecycleOwner) {
            it.data?.let { list ->
                binding.progressBar.hideView()
                adapter.setWallPaperList(list)
                binding.textViewNoFavouriteWallPaper.hideView()
            } ?: run {
                binding.progressBar.hideView()
                binding.textViewNoFavouriteWallPaper.showView()
            }
        }
    }

}