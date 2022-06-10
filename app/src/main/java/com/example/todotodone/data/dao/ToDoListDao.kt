package com.example.todotodone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotodone.data.entities.ToDoList

@Dao
interface ToDoListDao {
    @Query("SELECT * FROM todo_list")
    fun getAll(): LiveData<List<ToDoList>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg toDoLists: ToDoList)

    @Delete
    fun delete(toDoList: ToDoList)
}