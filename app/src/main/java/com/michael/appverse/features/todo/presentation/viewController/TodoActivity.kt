package com.michael.appverse.features.todo.presentation.viewController

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.michael.appverse.R
import com.michael.appverse.databinding.ActivityTodoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {


    private var binding: ActivityTodoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Set up the action bar.
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.finish()
    }



}