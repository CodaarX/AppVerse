package com.decagon.n26_p3_usecase.core.baseClasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.decagon.n26_p3_usecase.R

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}