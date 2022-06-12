package com.example.todotodone.ui.projects

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todotodone.R
import com.example.todotodone.data.entities.Project
import com.example.todotodone.ui.core.StyledFloatingActionButton
import com.example.todotodone.ui.core.TextDialog
import com.example.todotodone.ui.theme.ToDoToDoneTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectListScreen(
    modifier: Modifier = Modifier,
    projectListViewModel: ProjectListViewModel = hiltViewModel(),
    onProjectClick: (String) -> Unit,
) {
    val projects by projectListViewModel.projects.observeAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            StyledFloatingActionButton(
                icon = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.new_list),
                onClick = { openDialog = true }
            )
        },
        modifier = modifier
    ) {
        if (projects.isNullOrEmpty()) {
            EmptyProjectList()
        } else {
            ProjectList(
                list = projects!!,
                scaffoldState = scaffoldState,
                onDelete = { projectListViewModel.deleteProject(it) },
                onUndoDelete = { projectListViewModel.undoDeleteProject(it) },
                onProjectClick = onProjectClick,
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
            { newProjectName ->
                projectListViewModel.addProject(newProjectName)
                openDialog = false
            }
        )
    }
}

@Composable
fun ProjectList(
    list: List<Project>,
    scaffoldState: ScaffoldState,
    onDelete: (Project) -> Unit,
    onUndoDelete: (Project) -> Unit,
    onProjectClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { project -> project.id }
        ) { project ->
            ProjectCard(
                name = project.name,
                onClick = { onProjectClick(project.name) },
                onDeleteClick = {
                    scope.launch {
                        onDelete(project)
                        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                            "Deleted ${project.name}",
                            actionLabel = "Undo"
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            onUndoDelete(project)
                        }
                    }
                }
            )
        }
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
        ProjectListScreen(onProjectClick = {})
    }
}