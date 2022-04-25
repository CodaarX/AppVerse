package com.michael.appverse.features.programmingJokes.remoteService

import com.michael.appverse.features.programmingJokes.model.JokeModelRaw
import retrofit2.Response
import retrofit2.http.GET

interface JokesApiService {

    @GET("Programming")
    suspend fun getProgrammingJoke() : Response<JokeModelRaw>

    @GET("Any")
    suspend fun getAnyJoke() : Response<JokeModelRaw>


}