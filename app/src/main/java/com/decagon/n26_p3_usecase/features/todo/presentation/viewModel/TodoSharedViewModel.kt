package com.decagon.n26_p3_usecase.features.todo.presentation.viewModel

import android.app.Application
import android.graphics.Color.red
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.decagon.n26_p3_usecase.R
import com.decagon.n26_p3_usecase.commons.utils.log
import com.decagon.n26_p3_usecase.features.todo.model.Priority
import com.decagon.n26_p3_usecase.features.todo.model.TodoData

class TodoSharedViewModel(application: Application) : AndroidViewModel(application) {

//    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(true)
//
//    fun checkIfDatabaseIsEmpty(todoList: List<TodoData>) {
//        emptyDatabase.value = todoList.isEmpty()
//    }

    val listener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
            when(position) {
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red_900))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow_600))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green_300))}
            }
        }
    }

     fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority" -> Priority.LOW
            else -> Priority.HIGH
        }
    }

    fun verifyInputs(title: String, description: String): Boolean =  title.isNotEmpty() && description.isNotEmpty()

    fun parsePrioritytoInt(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
    }
}