package com.michael.appverse.features.wallpaper.presentation.viewController

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michael.appverse.databinding.ActivityMainBinding


class WallPaperActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

    }
}