package com.decagon.n26_p3_usecase.core.data.remote

import com.decagon.n26_p3_usecase.core.domain.model.jokesModel.JokeModelRaw
import retrofit2.Response
import retrofit2.http.GET

interface JokesApiService {
    @GET("Programming")
    suspend fun getProgrammingJoke() : Response<JokeModelRaw>

    @GET("Any")
    suspend fun getAnyJoke() : Response<JokeModelRaw>
}