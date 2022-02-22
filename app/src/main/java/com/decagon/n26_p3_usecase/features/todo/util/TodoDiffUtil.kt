package com.decagon.n26_p3_usecase.features.todo.util

import androidx.recyclerview.widget.DiffUtil
import com.decagon.n26_p3_usecase.features.todo.model.TodoData

class TodoDiffUtil(private val oldList: List<TodoData>, private val newList: List<TodoData>) :DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return when(oldList){
            is List<TodoData> -> {
                 oldList[oldItemPosition].id == newList[newItemPosition].id
                        && oldList[oldItemPosition].title == newList[newItemPosition].title
                        && oldList[oldItemPosition].description == newList[newItemPosition].description
                        && oldList[oldItemPosition].priority == newList[newItemPosition].priority
            }
            else -> {
                return false
            }
        }

    }

}