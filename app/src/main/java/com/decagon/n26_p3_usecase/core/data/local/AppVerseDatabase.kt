package com.decagon.n26_p3_usecase.core.data.local

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decagon.n26_p3_usecase.commons.utils.Converter
import com.decagon.n26_p3_usecase.features.locationTracker.data.dao.RunDao
import com.decagon.n26_p3_usecase.features.locationTracker.model.Run
import com.decagon.n26_p3_usecase.features.todo.data.dao.TodoDao
import com.decagon.n26_p3_usecase.features.todo.model.TodoData


@Database(entities = [TodoData::class, Run::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppVerseDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun runDao(): RunDao

    companion object {

        // static way to create db - ensuring the  singleton instance
//        @Volatile
//        private var INSTANCE: TodoDatabase? = null
//
//        fun getInstance(context: Context) : TodoDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(context, TodoDatabase::class.java, DATABASE_NAME).build()
//
//            }
//            INSTANCE =  tempInstance
//            return INSTANCE!!
//        }

        const val DATABASE_NAME = "appVerse_database"
    }
}
