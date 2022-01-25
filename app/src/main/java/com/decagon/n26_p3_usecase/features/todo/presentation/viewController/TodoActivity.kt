package com.decagon.n26_p3_usecase.features.todo.presentation.viewController

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.decagon.n26_p3_usecase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set up the action bar.
        setupActionBarWithNavController(findNavController(com.decagon.n26_p3_usecase.R.id.nav_host_fragment))

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(com.decagon.n26_p3_usecase.R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}