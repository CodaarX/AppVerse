package com.michael.appverse.commons.utils
//
//import android.text.Editable
//import android.text.TextWatcher
//import android.view.View
//import android.widget.SearchView
//import android.widget.TextView
//
//object TextChangeListener {
//    fun observe(view: TextView,  SearchLocation: (location: String) -> Unit): String {
//        view.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable) {
//                if (s.isNotEmpty()){
//                    return s.toString()
//                }
//            }
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//                if (s.isNotEmpty()){
//                    return s.toString()
//                }
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
//                if (s.isNotEmpty()){
//                    return s.toString()
//                }
//            }
//        })
//    }
//}