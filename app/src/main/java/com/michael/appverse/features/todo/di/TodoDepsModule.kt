package com.michael.appverse.features.todo.di

import com.michael.appverse.features.todo.data.dao.TodoDao
import com.michael.appverse.core.data.local.AppVerseDatabase
import com.michael.appverse.features.todo.data.repository.contract.TodoRepository
import com.michael.appverse.features.todo.data.repository.implementation.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDepsModule {

    @Singleton
    @Provides
    fun provideTodoDao(appVerseDatabase: AppVerseDatabase): TodoDao {
        return appVerseDatabase.todoDao()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(appVerseDatabase: AppVerseDatabase): TodoRepository {
        return TodoRepositoryImpl(appVerseDatabase)
    }

}
