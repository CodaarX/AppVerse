package com.michael.appverse.features.locationTracker.presentation.viewController

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.michael.appverse.R
import com.michael.appverse.databinding.ActivityTrackLocationBinding
import com.michael.appverse.features.locationTracker.utils.TrackingUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackLocationActivity : AppCompatActivity() {

    private var binding: ActivityTrackLocationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackLocationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navigateToTrackingFragmentIfNeeded(intent) // activity dead and intent is sent to activity

    }


    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?){
        if (intent?.action == TrackingUtils.ACTION_SHOW_TRACKING_FRAGMENT){ // if the intent is to show the tracking fragment
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.run_tracker_navigator_host) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.trackcingFragment)
        }
    }

    override fun onNewIntent(intent: Intent?) { // activity alive and intent is coming
        super.onNewIntent(intent)
         navigateToTrackingFragmentIfNeeded(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.run_tracker_navigator_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}