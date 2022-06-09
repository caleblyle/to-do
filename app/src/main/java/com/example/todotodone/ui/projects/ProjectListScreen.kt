package com.example.todotodone.ui.projects

import android.content.res.Configuration
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todotodone.R
import com.example.todotodone.ui.theme.ToDoToDoneTheme

@Composable
fun ProjectListScreen() {
    Scaffold(
        floatingActionButton = { MyFloatingActionButton() }
    ) {
        EmptyProjectList()
    }
}

@Composable
fun MyFloatingActionButton() {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.new_list)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "DefaultPreviewDark")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoToDoneTheme {
        ProjectListScreen()
    }
}