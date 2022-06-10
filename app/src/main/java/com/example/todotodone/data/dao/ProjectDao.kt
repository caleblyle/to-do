package com.example.todotodone.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todotodone.data.entities.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM projects")
    fun getAll(): LiveData<List<Project>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg projects: Project)

    @Delete
    fun delete(project: Project)

}