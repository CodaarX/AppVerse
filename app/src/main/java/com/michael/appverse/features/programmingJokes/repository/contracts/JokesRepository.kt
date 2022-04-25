package com.michael.appverse.features.programmingJokes.repository.contracts

import com.michael.appverse.features.programmingJokes.model.JokeModelRaw
import retrofit2.Response

interface JokesRepository {

    suspend fun getAnyJoke() : Response<JokeModelRaw>

    suspend fun getProgrammingJoke() : Response<JokeModelRaw>

}