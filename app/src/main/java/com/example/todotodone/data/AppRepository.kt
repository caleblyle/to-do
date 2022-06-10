package com.example.todotodone.data

import androidx.lifecycle.LiveData
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.dao.ToDoListDao
import com.example.todotodone.data.entities.ToDoList
import java.util.*
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val toDoListDao: ToDoListDao,
    private val taskDao: TaskDao
) {

    fun getProjects(): LiveData<List<ToDoList>> = toDoListDao.getAll()

    fun addProject(name: String) {
        val newToDoList = ToDoList(
            id = 0,
            listName = name,
            creationDate = Date()
        )
        toDoListDao.insert(newToDoList)
    }
}