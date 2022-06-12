package com.example.todotodone.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotodone.data.repositories.ProjectRepository
import com.example.todotodone.data.entities.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private val _projects = projectRepository.getProjects()

    val projects: LiveData<List<Project>>
        get() = _projects

    fun addProject(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.addProject(name)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.deleteProject(project)
        }
    }

    fun undoDeleteProject(project: Project) {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.undoDeleteProject(project)
        }
    }

}