package com.example.todotodone.data.repositories

import androidx.lifecycle.LiveData
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.entities.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    fun getTasksForProject(projectId: Int): LiveData<List<Task>> =
        taskDao.getTasksForToDoList(projectId)

    fun deleteTask(task: Task) {
        task.isDeleted = true
        taskDao.update(task)
    }

    fun undoDeleteTask(task: Task) {
        task.isDeleted = false
        taskDao.update(task)
    }

    fun addTask(projectId: Int, name: String) {
        val newTask = Task(projectId = projectId, taskDescription = name)
        taskDao.insert(newTask)
    }


}