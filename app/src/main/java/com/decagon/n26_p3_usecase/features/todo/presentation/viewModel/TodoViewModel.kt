package com.decagon.n26_p3_usecase.features.todo.presentation.viewModel

import androidx.lifecycle.*
import com.decagon.n26_p3_usecase.features.todo.data.repository.contract.TodoRepository
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(val repository: TodoRepository) : ViewModel() {

    // db



    private val _todoList : MutableLiveData<List<TodoData>> = MutableLiveData()
    val todoList : LiveData<List<TodoData>> = _todoList

    val sortByHighPriority : LiveData<List<TodoData>> = Transformations.map(_todoList) {
        it.sortedByDescending { it.priority }
    }
    val sortByLowPriority : LiveData<List<TodoData>> = Transformations.map(_todoList) {
        it.sortedBy { it.priority }
    }

    fun getAllTodo(){
        viewModelScope.launch {
            repository.getTodos().collect {
                _todoList.value = it
            }
        }
    }

    fun addToDB(todo: TodoData){
        viewModelScope.launch {
            repository.addTodo(todo).collect {
                _todoList.value = it
            }
        }
    }

    fun updateTodo(todo: TodoData){
        viewModelScope.launch {
            repository.updateTodo(todo).collect {
                _todoList.value = it
            }
        }
    }

    fun deleteTodo(todo: TodoData){
        viewModelScope.launch {
            repository.deleteTodo(todo).collect {
                _todoList.value = it
            }
        }
    }

    fun deleteAllTodo(){
        viewModelScope.launch {
            repository.deleteAllTodos().collect {
                _todoList.value = it
            }
        }
    }

    fun searchTodo(search: String){
        viewModelScope.launch {
            repository.searchTodo(search).collect {
                _todoList.value = it
            }
        }
    }

}