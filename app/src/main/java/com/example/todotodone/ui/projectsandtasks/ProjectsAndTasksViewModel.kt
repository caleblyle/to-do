package com.example.todotodone.ui.projectsandtasks

import android.util.Log
import androidx.lifecycle.*
import com.example.todotodone.data.entities.Project
import com.example.todotodone.data.entities.Task
import com.example.todotodone.data.repositories.ProjectRepository
import com.example.todotodone.data.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsAndTasksViewModel @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _projects = projectRepository.getProjects()
    private val _selectedProjectId: MutableLiveData<Int> = MutableLiveData()
    private val _tasks: LiveData<List<Task>> = Transformations.switchMap(_selectedProjectId)  {
        taskRepository.getTasksForProject(it)
    }

    val tasks: LiveData<List<Task>>
        get() = _tasks

    val projects: LiveData<List<Project>>
        get() = _projects

    val selectedProjectId: LiveData<Int>
        get() = _selectedProjectId

    fun selectProject(projectId: Int) {
        _selectedProjectId.postValue(projectId)
    }

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

    fun addTask(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(_selectedProjectId.value != null) {
                taskRepository.addTask(_selectedProjectId.value!!, name)
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }

    fun undoDeleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.undoDeleteTask(task)
        }
    }

    fun changeTaskCompletion(task: Task, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.changeCompletionState(task, checked)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO)  {
            val firstId = projectRepository.getProjects().value?.firstOrNull()
            Log.i("Test 1", "First Id: $firstId")
            if(firstId != null) {
                _selectedProjectId.postValue(firstId.id)
            }
        }
    }

}