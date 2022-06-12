package com.example.todotodone.ui

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todotodone.ui.core.AppNavHost
import com.example.todotodone.ui.theme.ToDoToDoneTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainApp() {
    ToDoToDoneTheme {
        val navController = rememberAnimatedNavController()
        AppNavHost(navController = navController)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainApp()
}