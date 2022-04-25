package com.michael.appverse.features.todo.data.repository.implementation

import kotlinx.coroutines.flow.flow

fun <DB> baseRepository(
    fetchFromLocal: suspend () -> DB,
    performOperation: suspend () -> Unit,
) = flow {
    performOperation.invoke()
    emit(fetchFromLocal())
}
