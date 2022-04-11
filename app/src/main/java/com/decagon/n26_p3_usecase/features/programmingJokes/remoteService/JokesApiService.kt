package com.decagon.n26_p3_usecase.features.programmingJokes.remoteService

import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokeModelRaw
import retrofit2.Response
import retrofit2.http.GET

interface JokesApiService {

    @GET("Programming")
    suspend fun getProgrammingJoke() : Response<JokeModelRaw>

    @GET("Any")
    suspend fun getAnyJoke() : Response<JokeModelRaw>


}