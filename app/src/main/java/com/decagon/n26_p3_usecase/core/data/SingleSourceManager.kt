package com.decagon.n26_p3_usecase.core.data

import com.decagon.n26_p3_usecase.commons.utils.Resource
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

inline fun <DB, API> singleSourceManager(
    crossinline fetchFromLocal: suspend () -> Flow<DB>,
    crossinline fetchFromRemote: suspend () -> API,
    crossinline saveToLocalDB: suspend (API) -> Unit,
    crossinline shouldFetchFromRemote: () -> Boolean
) = flow {
    emit(Resource.Loading("Fetching data"))
    val localData = fetchFromLocal().first()
    val dataStream = if (shouldFetchFromRemote()) {
        try {
            saveToLocalDB(fetchFromRemote())
            fetchFromLocal().map { dbData -> Resource.Success(dbData) }
        } catch (throwable: Throwable) {
            fetchFromLocal().map {
                Resource.ErrorWithData(throwable.message, it)
            }
        }
    } else {
        fetchFromLocal().map { Resource.Success(it) }
    }
    emitAll(dataStream)
}
