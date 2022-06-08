package com.example.todotodone.data.dao

import androidx.room.*
import com.example.todotodone.data.entities.Task

@Dao
interface TaskDao {

    @Query("""
        SELECT * 
        FROM tasks 
        WHERE todo_list_id = :toDoListId 
    """)
    fun getTasksForToDoList(toDoListId: Int) : List<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg task: Task)

    @Delete
    fun delete(task: Task)
}