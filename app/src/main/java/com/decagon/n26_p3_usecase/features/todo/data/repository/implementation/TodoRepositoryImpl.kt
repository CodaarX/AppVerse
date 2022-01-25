package com.decagon.n26_p3_usecase.features.todo.data.repository.implementation

import com.decagon.n26_p3_usecase.core.data.local.AppVerseDatabase
import com.decagon.n26_p3_usecase.features.todo.data.repository.contract.TodoRepository
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val database: AppVerseDatabase) : TodoRepository {

    override suspend fun getTodos(): Flow<List<TodoData>> =
        flow {
            emit(database.todoDao().getAllData())
        }


    override suspend fun addTodo(todo: TodoData) {
        database.todoDao().insertData(todo)
    }

//    override suspend fun deleteTodo(todo: TodoData) {
//        database.todoDao().deleteData(todo)
//    }
}