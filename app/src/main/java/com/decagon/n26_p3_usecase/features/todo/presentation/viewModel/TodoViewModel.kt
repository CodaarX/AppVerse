package com.decagon.n26_p3_usecase.features.todo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.decagon.n26_p3_usecase.features.todo.data.repository.contract.TodoRepository
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(val repository: TodoRepository) : ViewModel() {

    private val _todoList : MutableLiveData<List<TodoData>> = MutableLiveData()
    val todoList : LiveData<List<TodoData>> = _todoList


    init {
        getAllTodo()
    }



    private fun getAllTodo(){
        viewModelScope.launch {
            repository.getTodos().collect {
                _todoList.value = it
            }
        }
    }

    fun addToDB(todo: TodoData){
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }

//    fun deleteTodo(todo: TodoData){
//        viewModelScope.launch {
//            repository.deleteTodo(todo)
//        }
//    }

}