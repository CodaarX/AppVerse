package com.decagon.n26_p3_usecase.features.splashScreen

import android.content.Intent
import android.media.MediaPlayer
import android.media.VolumeShaper
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.decagon.n26_p3_usecase.core.data.preferences.SharedPreference
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.animations.Animator
import com.decagon.n26_p3_usecase.commons.utils.Constants
import com.decagon.n26_p3_usecase.core.MainActivity
import com.decagon.n26_p3_usecase.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {


    @Inject
    lateinit var sharedPreference: SharedPreference


    private val binding get() = _binding!!
    private var _binding: ActivitySplashScreenBinding? = null

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hide action bar
        supportActionBar?.hide()

        // set binding
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimation()

        /*Move to the home page after showing splash screen for X milliseconds*/
        GlobalScope.launch {
            delay(5000L)
            withContext(Dispatchers.Main) {

                val welcome = sharedPreference.loadFromSharedPref(Constants.WELCOME)

//                if(welcome.isEmpty()){
//                    val intent = Intent(this@SplashScreenActivity, OnBoardingActivity::class.java)
//                    startActivity(intent)
//                    Animator.animateActivityFadeContext(this@SplashScreenActivity)
//                    finish()
//                } else {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    Animator.animateActivityFadeContext(this@SplashScreenActivity)
                    finish()
//                }
            }
        }
    }


    private fun startAnimation(){
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun fadeInOrOutAudio(mediaPlayer: MediaPlayer, duration: Long, out: Boolean) {
        val config = if (out) fadeOutConfig(duration) else fadeInConfig(duration)
        val volumeShaper = mediaPlayer.createVolumeShaper(config)
        volumeShaper.apply(VolumeShaper.Operation.PLAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}