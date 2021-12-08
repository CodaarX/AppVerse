package com.decagon.n26_p3_usecase.features.programmingJokes.model

data class JokesModelSafe(
    val category: String,
    val id: Int,
    val joke: String? = null,
    val type: String,
    val setup: String? = null,
    val delivery: String? = null,
)