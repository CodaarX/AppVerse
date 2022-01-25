package com.decagon.n26_p3_usecase.commons.utils

import com.decagon.n26_p3_usecase.features.todo.model.Priority

object Helpers {

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            "Low Priority " -> Priority.LOW
            else -> Priority.LOW
        }
    }

    fun verifyInputs(title: String, description: String): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }

}