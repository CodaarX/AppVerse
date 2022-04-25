package com.michael.appverse.core.baseClasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.michael.appverse.R

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}