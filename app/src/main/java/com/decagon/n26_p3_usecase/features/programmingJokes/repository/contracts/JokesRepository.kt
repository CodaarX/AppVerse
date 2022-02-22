package com.decagon.n26_p3_usecase.features.programmingJokes.repository.contracts

import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokeModelRaw
import retrofit2.Response

interface JokesRepository {

    suspend fun getAnyJoke() : Response<JokeModelRaw>

    suspend fun getProgrammingJoke() : Response<JokeModelRaw>

}