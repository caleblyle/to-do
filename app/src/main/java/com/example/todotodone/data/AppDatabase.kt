package com.example.todotodone.data

import android.content.Context
import androidx.room.*
import com.example.todotodone.data.converters.Converters
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.dao.ToDoListDao
import com.example.todotodone.data.entities.Task
import com.example.todotodone.data.entities.ToDoList

@Database(
    entities = [
        ToDoList::class,
        Task::class
    ],
    version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    //DAOs
    abstract fun toDoListDao(): ToDoListDao
    abstract fun taskDao(): TaskDao

    companion object {

        //Singleton to get the database instance
        @Volatile
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return appDatabase ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database"
                ).build()
                appDatabase = db
                db
            }
        }
    }
}