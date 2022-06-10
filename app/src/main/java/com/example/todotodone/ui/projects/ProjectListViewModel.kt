package com.example.todotodone.ui.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todotodone.data.AppRepository
import com.example.todotodone.data.entities.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _projects = appRepository.getProjects()

    val projects: LiveData<List<Project>>
        get() = _projects

    fun addProject(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.addProject(name)
        }
    }

}