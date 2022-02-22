package com.decagon.n26_p3_usecase.features.todo.data.repository.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.features.todo.data.repository.contract.TodoRepository
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val database: AppVerseDatabase) : TodoRepository {

    override suspend fun getTodos(): Flow<List<TodoData>> =

        flow {
            emit(getdata())
        }

    override suspend fun addTodo(todo: TodoData) : Flow<List<TodoData>> =

        baseRepository(
            fetchFromLocal = { getdata() },
            performOperation = { database.todoDao().insertData(todo) },
        )


    override suspend fun updateTodo(todo: TodoData): Flow<List<TodoData>> =
        baseRepository(
            fetchFromLocal = { getdata() },
            performOperation = { database.todoDao().updateData(todo) },
        )

    override suspend fun deleteTodo(todo: TodoData): Flow<List<TodoData>> =
        baseRepository(
            fetchFromLocal = { getdata() },
            performOperation = { database.todoDao().deleteData(todo) },
        )

    override suspend fun deleteAllTodos() : Flow<List<TodoData>> =
        baseRepository(
            fetchFromLocal = { getdata() },
            performOperation = { database.todoDao().deleteAllData() },
        )

    override suspend fun searchTodo(search: String): Flow<List<TodoData>> = database.todoDao().getDataByTitle(search).asFlow()

    suspend fun getdata() : List<TodoData> = database.todoDao().getAllData()

}