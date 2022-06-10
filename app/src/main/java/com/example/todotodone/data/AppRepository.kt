package com.example.todotodone.data

import androidx.lifecycle.LiveData
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.dao.ProjectDao
import com.example.todotodone.data.entities.Project
import java.util.*
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val projectDao: ProjectDao,
    private val taskDao: TaskDao
) {

    fun getProjects(): LiveData<List<Project>> = projectDao.getAll()

    fun deleteProject(todoList: Project) = projectDao.delete(todoList)

    fun addProject(name: String) {
        val newProject = Project(
            id = 0,
            name = name,
            creationDate = Date()
        )
        projectDao.insert(newProject)
    }


}