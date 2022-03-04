package com.decagon.n26_p3_usecase.features.programmingJokes.usecase.implementations

import com.decagon.n26_p3_usecase.core.data.BaseRepositoryRemoteOperation
import com.decagon.n26_p3_usecase.features.programmingJokes.repository.contracts.JokesRepository
import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.toJokesModelSafe
import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokesModelSafe
import com.decagon.n26_p3_usecase.features.programmingJokes.usecase.contracts.AnyJokesUseCase
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

