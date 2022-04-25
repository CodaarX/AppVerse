package com.michael.appverse.commons.utils

sealed class Resource<T>(){
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    data class Loading<T>(val message: String) : Resource<T>()
    data class ErrorWithData<Q, T>(val message: Q, val data: T) : Resource<T>()
    class Empty<T>() : Resource<T>()
}