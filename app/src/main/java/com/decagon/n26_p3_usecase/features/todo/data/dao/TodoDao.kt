package com.decagon.n26_p3_usecase.features.todo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    suspend fun getAllData() : List<TodoData>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertData(data: TodoData)

//    @Query("DELETE FROM todo_table")
//    suspend fun deleteData(data: TodoData)

//    @Query("DELETE FROM todo_table WHERE id = :id")
//    suspend fun updateData(id: Int)
}