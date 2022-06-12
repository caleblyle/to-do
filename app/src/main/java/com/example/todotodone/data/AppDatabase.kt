package com.example.todotodone.data

import android.content.Context
import androidx.room.*
import com.example.todotodone.data.converters.Converters
import com.example.todotodone.data.dao.ProjectDao
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.entities.Task
import com.example.todotodone.data.entities.Project
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    entities = [
        Project::class,
        Task::class
    ],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    //DAOs
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao

    companion object {

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "todo_database"
            ).build()
        }

    }
}