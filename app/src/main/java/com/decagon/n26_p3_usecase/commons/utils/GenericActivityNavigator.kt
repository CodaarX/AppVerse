package com.decagon.n26_p3_usecase.commons.utils

import android.content.Context
import android.content.Intent

object GenericActivityNavigator {
    inline fun <reified T> navigateToActivity(context: Context, destination: T){
        val intent = Intent(context, T::class.java)
        context.startActivity(intent)
    }

}