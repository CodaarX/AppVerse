package com.decagon.n26_p3_usecase.features.wallpaper.presentation.viewController

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagon.n26_p3_usecase.databinding.ActivityMainBinding


class WallPaperActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

    }
}