package com.example.todotodone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotodone.data.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM projects WHERE is_deleted = 0")
    fun getAll(): LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg projects: Project)

    @Update
    fun update(project: Project)

}