package com.decagon.n26_p3_usecase.data.repository.contracts

import com.decagon.n26_p3_usecase.data.remote.JokesApiService
import com.decagon.n26_p3_usecase.domain.model.JokeModel
import com.decagon.n26_p3_usecase.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface JokesRepository {

    suspend fun getAnyJoke() : Flow<Resource<JokeModel>>

    suspend fun getProgrammingJoke() : Flow<Resource<JokeModel>>

}