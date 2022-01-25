package com.decagon.n26_p3_usecase.features.todo.data.repository.contract

import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodos(): Flow<List<TodoData>>
    suspend fun addTodo(todo: TodoData)
//    suspend fun deleteTodo(todo: TodoData)
}