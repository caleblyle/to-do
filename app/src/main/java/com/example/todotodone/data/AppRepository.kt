package com.example.todotodone.data

import androidx.lifecycle.LiveData
import com.example.todotodone.data.dao.ProjectDao
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.entities.Project
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val projectDao: ProjectDao,
    private val taskDao: TaskDao
) {

    fun getProjects(): LiveData<List<Project>> = projectDao.getAll()

    fun deleteProject(project: Project) {
        project.isDeleted = true
        projectDao.update(project)
    }

    fun undoDeleteProject(project: Project) {
        project.isDeleted = false
        projectDao.update(project)
    }

    fun addProject(name: String) {
        val newProject = Project(name = name)
        projectDao.insert(newProject)
    }


}