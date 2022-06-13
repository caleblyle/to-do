package com.example.todotodone.data.repositories

import androidx.lifecycle.LiveData
import com.example.todotodone.data.dao.ProjectDao
import com.example.todotodone.data.entities.Project
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val projectDao: ProjectDao
) {

    fun getProjects(): LiveData<List<Project>> = projectDao.getAll()

    fun getProjectName(projectId: Int): LiveData<String> = projectDao.getProjectName(projectId)

    fun deleteProject(project: Project) {
        project.isDeleted = true
        projectDao.update(project)
    }

    fun deleteProjectById(projectId: Int) {
        projectDao.setProjectDeleted(projectId)
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