package com.example.todotodone.data.dao

import androidx.room.*
import com.example.todotodone.data.entities.Task

@Dao
interface TaskDao {

    @Query("""
        SELECT * 
        FROM tasks 
        WHERE project_id = :projectId 
    """)
    fun getTasksForToDoList(projectId: Int) : List<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg task: Task)

    @Delete
    fun delete(task: Task)
}