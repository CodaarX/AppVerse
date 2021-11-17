package com.decagon.n26_p3_usecase.data.repository.implementations

import com.decagon.n26_p3_usecase.data.remote.JokesApiService
import com.decagon.n26_p3_usecase.data.repository.contracts.JokesRepository
import com.decagon.n26_p3_usecase.domain.model.JokeModel
import com.decagon.n26_p3_usecase.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JokesRepositoryImpl  @Inject constructor(private val jokesAPiService: JokesApiService) : JokesRepository {

    override suspend fun getAnyJoke(): Flow<Resource<JokeModel>> =


        flow {
            emit (
                try {

//                    Resource.Loading<String>("Loading")

                    val response = jokesAPiService.getAnyJoke()
                    val result = response.body()

                    if (result != null && response.isSuccessful) {
                        Resource.Success(result)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (e: Exception) {
                    Resource.Error(e.message ?: "an error occurred")
                }
            )
        }


    override suspend fun getProgrammingJoke(): Flow<Resource<JokeModel>> =

            flow {
                emit (
                    try {
                        Resource.Loading<String>("Loading")

                        val response = jokesAPiService.getProgrammingJoke()
                        val result = response.body()

                        if (result != null && response.isSuccessful) {
                            Resource.Success(result)
                        } else {
                            Resource.Error(response.message())
                        }
                    } catch (e: Exception) {
                        Resource.Error(e.message ?: "an error occurred")
                    }
                )
            }
        }

