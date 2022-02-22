package com.decagon.n26_p3_usecase.core.baseClasses

import com.decagon.n26_p3_usecase.commons.utils.Resource
import com.decagon.n26_p3_usecase.features.programmingJokes.model.JokeModelRaw
import com.decagon.n26_p3_usecase.features.programmingJokes.model.toJokesModelSafe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepositoryRemoteOperation {
    suspend fun <T> getData (apiCall: suspend () -> Response<T>) : Flow<Resource<T>> =

            flow {
                emit (
                    try {
                        Resource.Loading<String>("Loading")

                        val response = apiCall.invoke()
                        val result = response.body()

                        if (result != null && response.isSuccessful) {
                            when (result) {
                               is JokeModelRaw -> Resource.Success<T>(result.toJokesModelSafe() as T)

                                else -> Resource.Empty()
                            }
                         } else {
                            Resource.Error(response.message())
                        }
                    } catch (e: Exception) {
                        Resource.Error(e.localizedMessage ?: "an error occurred")
                    }
                )
            }


}