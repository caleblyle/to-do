package com.example.todotodone.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "tasks")
data class Task(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "project_id")
    val projectId: Int,

    @ColumnInfo(name = "task_description")
    val taskDescription: String,

    @ColumnInfo(name = "creation_date")
    val creationDate: Date = Date(),

    @ColumnInfo(name = "is_complete")
    val isComplete: Boolean = false,

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false

) {

}
