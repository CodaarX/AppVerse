package com.decagon.n26_p3_usecase.features.programmingJokes.usecase.implementations

import com.decagon.n26_p3_usecase.core.baseClasses.BaseRepositoryRemoteOperation
import com.decagon.n26_p3_usecase.features.programmingJokes.repository.contracts.JokesRepository
import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.toJokesModelSafe
import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokesModelSafe
import com.decagon.n26_p3_usecase.features.programmingJokes.usecase.contracts.ProgrammingJokesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProgrammingJokesUseCaseImpl  @Inject constructor(private val jokesRepository: JokesRepository) : ProgrammingJokesUseCase, BaseRepositoryRemoteOperation() {


    override suspend fun invoke(): Flow<Resource<JokesModelSafe>> =
//        getData { jokesRepository.getProgrammingJoke() }

        flow {
            emit(
                try {
                    Resource.Loading<String>("Loading")

                    val response = jokesRepository.getProgrammingJoke()
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

