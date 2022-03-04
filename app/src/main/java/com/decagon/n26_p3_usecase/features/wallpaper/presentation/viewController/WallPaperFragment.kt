package com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewController

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.decagon.edconnect.utils.animations.*
import com.decagon.n26_p3_usecase.commons.ui.animations.CompositeViewPager2Transformer
import com.decagon.n26_p3_usecase.commons.ui.animations.ZoomOutPageTransformer
import com.decagon.n26_p3_usecase.commons.ui.log
import com.decagon.n26_p3_usecase.core.baseClasses.BaseFragment
import com.decagon.n26_p3_usecase.databinding.FragmentWallPaperBinding
import com.decagon.n26_p3_usecase.features.wallpaper.WallPaperConstants
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.adapter.WallPaperAdapter
import com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewModel.WallPaperViewModel
import com.example.mike_utils.MikeUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WallPaperFragment : BaseFragment() {

    private lateinit var binding: FragmentWallPaperBinding
    lateinit  var viewPager : ViewPager2
    val adapter: WallPaperAdapter = WallPaperAdapter()
    val viewModel: WallPaperViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWallPaperBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference.saveToSharedPref("BASE_URL", WallPaperConstants.wallPaperBaseUrl)

        setUpViewPager()
        setUpObserver()

        viewModel.getWallPaperList("random", WallPaperConstants.clientId, WallPaperConstants.per_Page)

    }

    private fun setUpViewPager(){
        viewPager = binding.vpSlider
        viewPager.adapter = adapter
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        viewPager.currentItem = 3
        viewPager.setPageTransformer(ZoomOutPageTransformer)
//        viewPager.setPageTransformer(ZoomOutTransformer)
//        viewPager.setPageTransformer(SwipeFadeAnimator)
//        viewPager.setPageTransformer(FadeTransformation)
//        viewPager.setPageTransformer(DepthPageTransformer)
//        viewPager.setPageTransformer(DepthReveal)
//        viewPager.setPageTransformer(CompositeViewPager2Transformer.getTransformation())

//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if (position == 0) {
//                    viewPager.setCurrentItem(adapter.itemCount - 2, false)
//                } else if (position == adapter.itemCount - 1) {
//                    viewPager.setCurrentItem(1, false)
//                }
//            }
//        })

    }

    private fun setUpObserver(){

        viewModel.wallPapers.observe(viewLifecycleOwner) { it ->
            it.data?.let { list ->
                adapter.setWallPaperList(list)
            }
        }

    }

//



}