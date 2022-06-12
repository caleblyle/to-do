package com.example.todotodone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotodone.data.entities.Task
import java.util.*

@Dao
interface TaskDao {

    @Query("""
        SELECT id, project_id, task_description, creation_date, is_complete, is_deleted
        FROM tasks 
        WHERE project_id = :projectId 
        AND is_deleted = 0
    """)
    fun getTasksForToDoList(projectId: Int) : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg task: Task)

    @Update
    fun update(task: Task)
}