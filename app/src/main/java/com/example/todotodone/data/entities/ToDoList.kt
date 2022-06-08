package com.example.todotodone.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "todo_list")
data class ToDoList(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "list_name")
    val listName: String,

    @ColumnInfo(name = "creation_date")
    val creationDate: Date

)
