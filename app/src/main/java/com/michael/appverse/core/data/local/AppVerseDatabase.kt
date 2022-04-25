package com.michael.appverse.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.michael.appverse.commons.utils.Converter
import com.michael.appverse.features.locationTracker.data.dao.RunDao
import com.michael.appverse.features.locationTracker.data.mediator.RunMediatorDao
import com.michael.appverse.features.locationTracker.model.Run
import com.michael.appverse.features.todo.data.dao.TodoDao
import com.michael.appverse.features.todo.model.TodoData
import com.michael.appverse.features.wallpaper.data.WallpaperDao
import com.michael.appverse.features.wallpaper.domain.model.WallPaperDataSafe


@Database(entities = [TodoData::class, Run::class, WallPaperDataSafe::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppVerseDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun runDao(): RunDao
    abstract fun runMediatorDao(): RunMediatorDao
    abstract fun wallPaperDao(): WallpaperDao

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
