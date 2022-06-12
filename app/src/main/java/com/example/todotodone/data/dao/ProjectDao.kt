package com.example.todotodone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotodone.data.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM projects WHERE is_deleted = 0")
    fun getAll(): LiveData<List<Project>>

    @Query("SELECT name FROM projects WHERE id = :projectId LIMIT 1")
    fun getProjectName(projectId: Int): LiveData<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg projects: Project)

    @Update
    fun update(project: Project)

    @Query("""
        UPDATE projects
        SET is_deleted = 1
        WHERE id = :projectId
    """)
    fun setProjectDeleted(projectId: Int)

}