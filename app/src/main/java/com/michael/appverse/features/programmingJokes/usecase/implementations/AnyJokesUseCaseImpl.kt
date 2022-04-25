package com.michael.appverse.features.programmingJokes.usecase.implementations

import com.michael.appverse.core.data.BaseRepositoryRemoteOperation
import com.michael.appverse.features.programmingJokes.repository.contracts.JokesRepository
import com.michael.appverse.commons.utils.Resource
import com.michael.appverse.features.programmingJokes.model.toJokesModelSafe
import com.michael.appverse.features.programmingJokes.model.JokesModelSafe
import com.michael.appverse.features.programmingJokes.usecase.contracts.AnyJokesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnyJokesUseCaseImpl  @Inject constructor(private val jokesRepository: JokesRepository) : AnyJokesUseCase,
    BaseRepositoryRemoteOperation() {

    override suspend fun invoke(): Flow<Resource<JokesModelSafe>> =

//         getData { jokesRepository.getAnyJoke() }
        flow {
            emit (
                try {
                    val response = jokesRepository.getAnyJoke()
                    val result = response.body()

                    if (result != null && response.isSuccessful) {
                        Resource.Success<JokesModelSafe>(result.toJokesModelSafe())
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (e: Exception) {
                    Resource.Error(e.message ?: "an error occurred")
                }
            )
        }
}

