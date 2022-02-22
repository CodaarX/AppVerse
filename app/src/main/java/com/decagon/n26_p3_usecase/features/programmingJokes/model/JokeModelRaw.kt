package com.decagon.n26_p3_usecase.features.programmingJokes.model

data class JokeModelRaw(
    val category: String,
    val error: Boolean,
    val flags: Flags,
    val id: Int,
    val joke: String? = null,
    val lang: String,
    val safe: Boolean,
    val type: String,
    val setup: String? = null,
    val delivery: String? = null,
)

fun JokeModelRaw.toJokesModelSafe() : JokesModelSafe {
    return JokesModelSafe(
        category = category,
        id = id,
        type = type,
        joke = joke,
        setup = setup,
        delivery = delivery
    )
}