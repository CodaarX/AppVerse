package com.decagon.n26_p3_usecase.features.todo.data.repository.implementation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

inline fun <DB> baseRepository(
    crossinline fetchFromLocal: suspend () -> DB,
    noinline performOperation: suspend () -> Unit,
) = flow {
    performOperation.invoke()
    emit(fetchFromLocal())
}
