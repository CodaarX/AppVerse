package com.michael.appverse.features.qrCode.utils

import com.google.gson.Gson

object ClassConverter {

     val gson = Gson()

    fun <T> toJson(value: T): String = gson.toJson(value)

    inline fun <reified T> toClass(value: String): T {
        return gson.fromJson(value, T::class.java)
    }

}