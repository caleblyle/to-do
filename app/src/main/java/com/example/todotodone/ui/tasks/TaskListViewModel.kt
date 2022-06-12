package com.example.todotodone.ui.tasks

import androidx.lifecycle.*
import com.example.todotodone.data.repositories.ProjectRepository
import com.example.todotodone.data.entities.Task
import com.example.todotodone.data.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val projectId: Int = savedStateHandle["projectId"]!!

    private val _tasks = taskRepository.getTasksForProject(projectId)
    private val _projectName = projectRepository.getProjectName(projectId)
    private val _isProjectDeleted = MutableLiveData(false)

    val tasks: LiveData<List<Task>>
        get() = _tasks

    val projectName: LiveData<String>
        get() = _projectName

    fun addTask(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.addTask(projectId, name)
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

    fun deleteProject() {
        viewModelScope.launch(Dispatchers.IO) {
            projectRepository.deleteProjectById(projectId)
            _isProjectDeleted.postValue(true)
        }
    }

    fun changeTaskCompletion(task: Task, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.changeCompletionState(task, checked)
        }
    }
}