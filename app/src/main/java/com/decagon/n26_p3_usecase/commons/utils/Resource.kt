package com.decagon.n26_p3_usecase.commons.utils

sealed class Resource<T>(){
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    data class Loading<T>(val message: String) : Resource<T>()
    class Empty<T>() : Resource<T>()

}