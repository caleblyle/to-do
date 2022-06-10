package com.example.todotodone.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.todotodone.ui.projects.ProjectListScreen
import com.example.todotodone.ui.theme.ToDoToDoneTheme

@Composable
fun MainApp() {
    ToDoToDoneTheme {
        val navController = rememberNavController()
        ProjectListScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainApp()
}