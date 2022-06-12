package com.example.todotodone.di

import android.content.Context
import com.example.todotodone.data.AppDatabase
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.dao.ProjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) = AppDatabase.buildDatabase(appContext)

    @Singleton
    @Provides
    fun provideProjectDao(appDatabase: AppDatabase) = appDatabase.projectDao()

    @Singleton
    @Provides
    fun provideTaskDao(appDatabase: AppDatabase) = appDatabase.taskDao()

}