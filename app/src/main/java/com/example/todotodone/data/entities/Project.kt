package com.example.todotodone.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "projects")
data class Project(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "creation_date")
    val creationDate: Date = Date(),

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false

)
