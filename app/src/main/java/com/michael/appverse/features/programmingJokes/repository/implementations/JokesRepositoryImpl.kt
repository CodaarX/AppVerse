package com.michael.appverse.features.programmingJokes.repository.implementations

import com.michael.appverse.features.programmingJokes.remoteService.JokesApiService
import com.michael.appverse.features.programmingJokes.repository.contracts.JokesRepository
import com.michael.appverse.features.programmingJokes.model.JokeModelRaw
import retrofit2.Response
import javax.inject.Inject

class JokesRepositoryImpl  @Inject constructor(private val jokesAPiService: JokesApiService) : JokesRepository {

    override suspend fun getAnyJoke(): Response<JokeModelRaw> = jokesAPiService.getAnyJoke()

    override suspend fun getProgrammingJoke(): Response<JokeModelRaw> = jokesAPiService.getProgrammingJoke()

}

