package com.decagon.n26_p3_usecase.core

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import com.decagon.n26_p3_usecase.databinding.ActivityMainBinding
import com.decagon.n26_p3_usecase.commons.utils.*
import dagger.hilt.android.AndroidEntryPoint
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.animations.ViewAnimation

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var actionBar: ActionBar? = null
    private lateinit var toolbar: Toolbar
    private lateinit var content_view: CardView
    private lateinit var navController: NavController
    private var isHide = true
    private val width_content = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.app_color)
        toolbar = findViewById(R.id.toolbar)
        content_view = findViewById(R.id.main_content)

        initToolbar()
        initNavigationMenu()
        initNavController()

//      Color.BLUE
//      val bar: android.app.ActionBar? = actionBar
//      bar?.setBackgroundDrawable(ColorDrawable(Color.rgb(19,48,81)))
//      supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.black)));
//      supportActionBar?.hide()
    }


    private fun initToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24)
        toolbar.navigationIcon!!.setColorFilter(
            resources.getColor(R.color.blue_700),
            PorterDuff.Mode.SRC_ATOP
        )
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeButtonEnabled(true)
        actionBar!!.title = "Tool Box"
    }

    private fun initNavigationMenu() {

        content_view.post {
            content_view.translationX = 0f
            content_view.radius = Tools.dpToPx(applicationContext, 0).toFloat()
        }

        toolbar.setNavigationOnClickListener { v: View? ->
            if (isHide) {
                showMenu(content_view)
            } else {
                hideMenu(content_view)
            }
        }
        Handler(this.mainLooper).postDelayed({ showMenu(content_view) }, 1000)
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun hideMenu(view: View): ObjectAnimator? {
        isHide = true
        view.let {
            view.animate()
                .scaleX(1f).scaleY(1f)
                .translationX(0f)
                .setDuration(1000)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        content_view.radius = Tools.dpToPx(applicationContext, 0).toFloat()
                    }
                })
                .start()
            binding?.menuLinear?.visibility = View.GONE
            return null
        }

    }

    private fun showMenu(view: View): ObjectAnimator? {
        binding?.menuLinear?.visibility = View.VISIBLE
        isHide = false
        view.animate()
            .scaleX(0.9f).scaleY(0.9f)
            .translationX(view.width / 2f)
            .setDuration(1000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    content_view.radius = Tools.dpToPx(applicationContext, 10).toFloat()
                }
            })
            .start()
        return null
    }

    override fun onBackPressed() {
        if (!isHide) {
            hideMenu(content_view)
        } else {
            super.onBackPressed()
        }
    }

    fun toggleMenu(view: View?) {
        if (!isHide) {
            hideMenu(content_view)
        } else {
            showMenu(content_view)
        }
    }

    fun onMenuClick(view: View?) {
        when (view) {
            binding?.radioFeature -> navController.navigate(R.id.onlineRadioFragment)
            binding?.barCodeFeature -> navController.navigate(R.id.qr_navigator)

            else -> navController.navigate(R.id.programmingJokesFragment)
        }
        hideMenu(content_view)
    }

}