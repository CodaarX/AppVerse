package com.decagon.n26_p3_usecase.features.todo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.decagon.n26_p3_usecase.features.todo.model.TodoData
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    suspend fun getAllData() : List<TodoData>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertData(data: TodoData)

    @Delete
    suspend fun deleteData(data: TodoData)

    @Update
    suspend fun updateData(data: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM todo_table WHERE title LIKE :title")
    fun getDataByTitle(title: String): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END ")
    fun sortByHighPriority(): LiveData<List<TodoData>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END ")
    fun sortByLowPriority(): LiveData<List<TodoData>>



}