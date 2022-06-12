package com.example.todotodone

import java.lang.Exception

enum class Screen {
    ProjectList,
    TaskList;

    companion object {
        fun getScreenFromRoute(route: String?) : Screen {
            return when(route?.substringBefore("/")) {
                ProjectList.name -> ProjectList
                TaskList.name -> TaskList
                else -> throw Exception("Unknown route $route")
            }
        }
    }
}