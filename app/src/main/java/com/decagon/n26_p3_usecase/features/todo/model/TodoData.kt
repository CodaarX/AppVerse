package com.decagon.n26_p3_usecase.features.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val completed: Boolean,
    val priority: Priority
)
