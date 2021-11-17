package com.decagon.n26_p3_usecase.data.repository

import com.decagon.n26_p3_usecase.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

//abstract class ApiCall {
//    suspend fun <T> call(apiCall: suspend () -> Response<T>) : Flow<Resource<T>> {
//
//        return withContext(Dispatchers.IO) {
//            flow {
//                emit (
//                    try { //                    Resource.Loading<String>("Loading")
//
//                        val response = apiCall.invoke()
//                        val result = response.body()
//
//                        if (result != null && response.isSuccessful) {
//                            Resource.Success(result)
//                        } else {
//                            Resource.Error(response.message())
//                        }
//                    } catch (e: Exception) {
//                        Resource.Error(e.localizedMessage ?: "an error occurred")
//                    }
//                )
//            }
//
//        }
//    }
//}