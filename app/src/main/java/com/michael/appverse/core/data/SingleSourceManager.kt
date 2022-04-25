package com.michael.appverse.core.data

import com.michael.appverse.commons.utils.Resource
import kotlinx.coroutines.flow.*

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
