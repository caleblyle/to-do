package com.example.todotodone.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.todotodone.ui.core.AppNavHost
import com.example.todotodone.ui.core.WindowSizeClass
import com.example.todotodone.ui.theme.ToDoToDoneTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainApp(windowSize: WindowSizeClass) {
    ToDoToDoneTheme {
        val navController = rememberAnimatedNavController()
        AppNavHost(navController = navController, windowSize = windowSize)
    }
}