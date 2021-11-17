package com.decagon.n26_p3_usecase.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(message = message)
    class Loading<T>(message: String) : Resource<T>(message = message)
}