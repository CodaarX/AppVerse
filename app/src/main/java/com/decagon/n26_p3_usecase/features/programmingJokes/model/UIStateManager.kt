package com.decagon.n26_p3_usecase.features.programmingJokes.model


data class UiStateManager<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String? = null
)