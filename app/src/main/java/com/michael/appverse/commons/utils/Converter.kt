package com.michael.appverse.commons.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.michael.appverse.features.todo.model.Priority
import com.michael.appverse.features.todo.model.TodoData
import com.google.gson.Gson
import java.io.ByteArrayOutputStream


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

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}