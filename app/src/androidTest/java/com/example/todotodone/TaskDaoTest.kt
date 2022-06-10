package com.example.todotodone

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.todotodone.data.AppDatabase
import com.example.todotodone.data.dao.TaskDao
import com.example.todotodone.data.entities.Task
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*

class TaskDaoTest {

    private lateinit var taskDao: TaskDao
    private lateinit var appDb: AppDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()

        taskDao = appDb.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTask() {
        val task = Task(0, 1, "Test", Date(), null)
        taskDao.insert(task)

        val tasks = taskDao.getTasksForToDoList(1)
        assertThat(tasks.first(), equalTo(task))
    }
}