package com.example.todotodone

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todotodone.ui.projects.ProjectListScreen
import com.example.todotodone.ui.theme.ToDoToDoneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoToDoneTheme {
                ProjectListScreen()
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoToDoneTheme {
        ProjectListScreen()
    }
}