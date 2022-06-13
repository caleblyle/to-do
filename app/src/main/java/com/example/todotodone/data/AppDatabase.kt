package com.example.todotodone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todotodone.data.converters.Converters
import com.example.todotodone.data.dao.ProjectDao
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.entities.Project
import com.example.todotodone.data.entities.Task

@Database(
    entities = [
        Project::class,
        Task::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    //DAOs
    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao

    companion object {

        fun buildDatabase(context: Context): AppDatabase {
            return Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "todo_database"
                )
                .fallbackToDestructiveMigration()
                .build()
        }

    }
}