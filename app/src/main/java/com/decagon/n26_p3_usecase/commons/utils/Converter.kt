package com.decagon.n26_p3_usecase.commons.utils

import androidx.room.TypeConverter
import com.decagon.n26_p3_usecase.features.todo.model.Priority
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import com.google.gson.Gson


class Converter {

    @TypeConverter
    fun formPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(",")
    }

    @TypeConverter
    fun fromString(string: String): List<String> {
        return string.split(",").map { it }
    }

    @TypeConverter
    fun todoDataListToJsonString(value: List<TodoData>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToTodoData(value: String) =
        Gson().fromJson(value, Array<TodoData>::class.java).toList()


}