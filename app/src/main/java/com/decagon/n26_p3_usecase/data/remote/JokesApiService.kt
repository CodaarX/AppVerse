package com.decagon.n26_p3_usecase.data.remote

import com.decagon.n26_p3_usecase.domain.model.JokeModel
import retrofit2.Response
import retrofit2.http.GET

interface JokesApiService {
    @GET("Programming")
    suspend fun getProgrammingJoke() : Response<JokeModel>

    @GET("Any")
    suspend fun getAnyJoke() : Response<JokeModel>
}