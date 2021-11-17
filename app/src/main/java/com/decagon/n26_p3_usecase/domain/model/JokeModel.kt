package com.decagon.n26_p3_usecase.domain.model

data class JokeModel(
    val category: String,
    val error: Boolean,
    val flags: Flags,
    val id: Int,
    val joke: String,
    val lang: String,
    val safe: Boolean,
    val type: String,
    val setup: String? = null,
    val delivery: String? = null,
)