package com.decagon.n26_p3_usecase.core.data

import com.decagon.n26_p3_usecase.commons.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

abstract class BaseRepositoryRemoteOperation {

    suspend inline fun <T> getData(crossinline apiCall: suspend () -> Response<T>): Flow<Resource<T>> =
        flow {
            emit(
                try {
                    Resource.Loading<String>("Loading")

                    val response = apiCall.invoke()
                    val result = response.body()

                    if (result != null && response.isSuccessful) {
                        Resource.Success(result as T)
                    } else {
                        Resource.Error(response.message())
                    }
                } catch (e: Exception) {
                    Resource.Error(e.localizedMessage ?: "an error occurred")
                }
            )
        }
}
