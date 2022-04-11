package com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewController

import `in`.myinnos.savebitmapandsharelib.SaveAndShare
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.animations.ZoomOutPageTransformer
import com.decagon.n26_p3_usecase.commons.ui.hideView
import com.decagon.n26_p3_usecase.commons.ui.showView
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.commons.utils.NetworkLiveData
import com.decagon.n26_p3_usecase.commons.utils.PixelCopyListener
import com.decagon.n26_p3_usecase.commons.utils.RandomDataGenerator
import com.decagon.n26_p3_usecase.commons.utils.ViewToBitmap
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.FragmentWallPaperBinding
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.adapter.WallPaperAdapter
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewModel.WallPaperViewModel
import com.decagon.n26_p3_usecase.features.wallpaper.utils.WallPaperConstants
import com.example.mike_utils.MikeNetworkLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class WallPaperFragment : BaseFragment() {

    private lateinit var binding: FragmentWallPaperBinding
    private lateinit  var viewPager : ViewPager2
    val viewModel: WallPaperViewModel by activityViewModels()
    private val adapter: WallPaperAdapter by lazy { WallPaperAdapter(viewModel) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (activity as MainActivity).supportActionBar?.title = "Wallpapers"
        binding = FragmentWallPaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
        setUpObserver()
        setUpClickListeners()
        binding.vpSlider.hideView()
        // set color of progress bar to white
        binding.progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY)
        binding.progressBar.showView()
        getWallPaperList()
    }

    private fun setUpViewPager(){
        viewPager = binding.vpSlider
        viewPager.adapter = adapter
        viewPager.clipToPadding = false
        viewPager.offscreenPageLimit = 3
        viewPager.currentItem = 3
        viewPager.setPageTransformer(ZoomOutPageTransformer)
    }

    private fun setUpClickListeners(){
        val imm = activity?.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager

        binding.textViewSearchWallPaper.setOnClickListener {
            binding.vpSlider.hideView()
            binding.progressBar.hideView()
            binding.textViewSearchWallPaper.requestFocus()
            activity?.currentFocus?.let {
                imm.showSoftInput(it, 0)
            }
        }

        binding.searchWallPaperIcon.setOnClickListener {
            val searchText = binding.textViewSearchWallPaper.text.toString().trim()
            if(searchText.length > 2){
                imm.hideSoftInputFromWindow(binding.textViewSearchWallPaper.windowToken, 0)
                toast(requireContext(),"Searching for $searchText")
                viewModel.getWallPaperList(searchText, WallPaperConstants.clientId, WallPaperConstants.per_Page, RandomDataGenerator.getRandomWallPaperPage())
                viewModel.currentQuery = searchText
            } else {
                toast(requireContext(),"Please enter a valid search query")
            }
            binding.textViewSearchWallPaper.setText("")
        }

        binding.refreshWallPaperButton.setOnClickListener { getWallPaperList() }

        binding.saveWallPaperButton.setOnClickListener { saveWallPaper() }

        binding.favouriteWallPaperButton.setOnClickListener {
           findNavController().navigate(R.id.action_wallPaperFragment_to_favouriteWallPaperFragment)
        }

    }

    private fun getWallPaperList(){
        binding.progressBar.showView()
        binding.vpSlider.hideView()
        val query = viewModel.currentQuery

        if(query.isNotEmpty()){
            viewModel.getWallPaperList(query, WallPaperConstants.clientId, WallPaperConstants.per_Page, RandomDataGenerator.getRandomWallPaperPage())
            viewModel.currentQuery = query
        } else {
            viewModel.getWallPaperList("random", WallPaperConstants.clientId, WallPaperConstants.per_Page, RandomDataGenerator.getRandomWallPaperPage())
            viewModel.currentQuery = "random"
        }
    }

    private fun setUpObserver(){
        viewModel.wallPapers.observe(viewLifecycleOwner) {
            it.data?.let { list ->
                binding.progressBar.hideView()
                binding.vpSlider.showView()
                adapter.setWallPaperList(list)
            }

            it.error?.let { error ->
                binding.progressBar.hideView()
                binding.vpSlider.hideView()
                if(NetworkLiveData.isNetworkAvailable()){ toast(requireContext(),error)
                } else { toast(requireContext(),"No internet connection") }
            }

            if (it.isLoading){
                binding.wallPaperFragmentSearchLayout.hideView()
                binding.progressBar.showView()
                binding.vpSlider.hideView()
            }
        }
    }

    private fun saveWallPaper(){
        // get the current wallpaper and make background of fragment transparent
        val wallpaper = binding.vpSlider.getChildAt(viewPager.currentItem)
        // convert the view to bitmap
        val bitmap = convertViewToBitmap(wallpaper)
        // get the current time
        val currentTime = System.currentTimeMillis()
        SaveAndShare.save(requireActivity(), bitmap, "$currentTime-WallPaper", "jpg","WallPaper")
    }

    private fun  convertViewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}