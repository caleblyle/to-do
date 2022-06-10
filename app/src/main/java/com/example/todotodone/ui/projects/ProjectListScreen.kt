package com.example.todotodone.ui.projects

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todotodone.R
import com.example.todotodone.data.entities.Project
import com.example.todotodone.ui.core.TextDialog
import com.example.todotodone.ui.theme.ToDoToDoneTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectListScreen(
    modifier: Modifier = Modifier,
    projectListViewModel: ProjectListViewModel = viewModel()
) {
    val projects by projectListViewModel.projects.observeAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            StyledFloatingActionButton {
                openDialog = true
            }
        },
        modifier = modifier
    ) {
        if (projects.isNullOrEmpty()) {
            EmptyProjectList()
        } else {
            ProjectList(
                list = projects!!,
                modifier = modifier
            )
        }
    }
    if (openDialog) {
        TextDialog(
            stringResource(id = R.string.new_list),
            stringResource(id = R.string.project_label),
            stringResource(id = R.string.cancel),
            stringResource(id = R.string.create),
            { openDialog = false },
            { newToDoListName ->
                projectListViewModel.addProject(newToDoListName)
                openDialog = false
            }
        )
    }
}

@Composable
fun ProjectList(list: List<Project>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { project -> project.id }
        ) { project ->
            ToDoListCard(
                name = project.name,
                onClick = { }
            )
        }
    }
}

@Composable
fun ToDoListCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(name)
            }
            //TODO: Context menu for delete?
        }
    }
}

@Composable
fun StyledFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.new_list)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoToDoneTheme {
        ProjectListScreen()
    }
}