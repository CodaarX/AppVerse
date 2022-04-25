package com.michael.appverse.features.programmingJokes.model


data class UiStateManager<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)