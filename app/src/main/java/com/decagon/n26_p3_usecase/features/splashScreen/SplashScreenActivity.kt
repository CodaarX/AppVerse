package com.decagon.n26_p3_usecase.features.splashScreen

import android.content.Intent
import android.media.VolumeShaper
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.ui.animations.Animator
import com.decagon.n26_p3_usecase.commons.ui.showView
import com.decagon.n26_p3_usecase.commons.ui.toast
import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import com.decagon.n26_p3_usecase.core.presentation.MainActivity
import com.decagon.n26_p3_usecase.databinding.ActivitySplashScreenBinding
import com.example.mike_utils.MikeUtils
import dagger.hilt.android.AndroidEntryPoint
import io.bloco.faker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    private val binding get() = _binding!!
    private var _binding: ActivitySplashScreenBinding? = null

    var faker = Faker()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide action bar
        supportActionBar?.hide()
        // set binding
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notFirstTime = sharedPreference.loadFromSharedPref<Boolean>("Boolean", "firstTime")

        if (notFirstTime) {
            binding.welcomeToTextView.showView()
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            Animator.animateActivityFadeContext(this@SplashScreenActivity)
            sharedPreference.saveToSharedPref("firstTime", true)
            finish()
        } else {
            toast(this, "$notFirstTime")

            startAnimation()

            lifecycleScope.launch {
                delay(3000L)
                withContext(Dispatchers.Main) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java) //
                    sharedPreference.saveToSharedPref("firstTime", true)
                    startActivity(intent)
                    Animator.animateActivityFadeContext(this@SplashScreenActivity)
                    finish()
                }
            }
        }
    }

    private fun startAnimation() {
        val logo = binding.logoImageView
        val welcomeView = binding.welcomeToTextView
        val explorerView = binding.articleExplorerTextView
        val top = AnimationUtils.loadAnimation(this, R.anim.top)
        val bottom = AnimationUtils.loadAnimation(this, R.anim.bottom)
        welcomeView.animation = top
        explorerView.animation = top
        logo.animation = bottom
    }

    // audio fades control
    @RequiresApi(Build.VERSION_CODES.O)
    fun fadeOutConfig(duration: Long): VolumeShaper.Configuration {
        val times = floatArrayOf(0f, 1f) // can add more points, volume points must correspond to time points
        val volumes = floatArrayOf(1f, 0f)
        return VolumeShaper.Configuration.Builder()
            .setDuration(duration)
            .setCurve(times, volumes)
            .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_CUBIC)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fadeInConfig(duration: Long): VolumeShaper.Configuration {
        val times = floatArrayOf(0f, 1f) // can add more points, volume points must correspond to time points
        val volumes = floatArrayOf(0f, 1f)
        return VolumeShaper.Configuration.Builder()
            .setDuration(duration)
            .setCurve(times, volumes)
            .setInterpolatorType(VolumeShaper.Configuration.INTERPOLATOR_TYPE_CUBIC)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
