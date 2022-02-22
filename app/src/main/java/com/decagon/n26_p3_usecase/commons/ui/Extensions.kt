package com.decagon.n26_p3_usecase.commons.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import com.decagon.n26_p3_usecase.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


fun <T> log(value : T){
    Log.i("INFORMATION", "log: ${value}")
}


fun toast(context: Context, message : String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showSnackBar(view: CardView, message: String){
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val view1 = snackbar.view
    val textView = view1.findViewById<TextView>(R.id.snackbar_text)
    textView.setTextColor(Color.RED)
    snackbar.show()
}

fun snack(view: View, message: String){
     Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}

fun View.hideView(){
    visibility = View.GONE
}

fun View.showView(){
    visibility = View.VISIBLE
}

fun <T> timber(message: T){
    Timber.d("TIMBER: ${message}")
}
