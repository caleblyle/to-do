package com.example.todotodone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotodone.data.entities.Task

@Dao
interface TaskDao {

    @Query("""
        SELECT * 
        FROM tasks 
        WHERE project_id = :projectId 
        AND is_deleted = 0
    """)
    fun getTasksForToDoList(projectId: Int) : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg task: Task)

    @Update
    fun update(task: Task)
}