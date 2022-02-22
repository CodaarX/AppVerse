package com.decagon.n26_p3_usecase.features.todo.data.repository.contract

import androidx.lifecycle.LiveData
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodos(): Flow<List<TodoData>>
    suspend fun addTodo(todo: TodoData) : Flow<List<TodoData>>
    suspend fun updateTodo(todo: TodoData) : Flow<List<TodoData>>
    suspend fun deleteTodo(todo: TodoData) : Flow<List<TodoData>>
    suspend fun deleteAllTodos() : Flow<List<TodoData>>
    suspend fun searchTodo(search: String) : Flow<List<TodoData>>
}