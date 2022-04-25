package com.michael.appverse.features.todo.data.repository.contract

import com.michael.appverse.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodos(): Flow<List<TodoData>>
    suspend fun addTodo(todo: TodoData): Flow<List<TodoData>>
    suspend fun updateTodo(todo: TodoData): Flow<List<TodoData>>
    suspend fun deleteTodo(todo: TodoData): Flow<List<TodoData>>
    suspend fun deleteAllTodos(): Flow<List<TodoData>>
    suspend fun searchTodo(search: String): Flow<List<TodoData>>
}
