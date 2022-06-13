package com.example.todotodone.ui.projects

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    onProjectClick: (Int) -> Unit,
) {
    val projects by projectListViewModel.projects.observeAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val undoString = stringResource(id = R.string.undo)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = { Text(stringResource(id = R.string.projects_title)) }) },
        floatingActionButton = {
            StyledFloatingActionButton(
                icon = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.new_project),
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
                onDeleteRequest = {
                    scope.launch {
                        projectListViewModel.deleteProject(it)
                        val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                            "Deleted ${it.name}",
                            actionLabel = undoString
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            projectListViewModel.undoDeleteProject(it)
                        }
                    }
                },
                onProjectClick = onProjectClick,
                modifier = modifier
            )
        }
    }
    if (openDialog) {
        TextDialog(
            stringResource(id = R.string.new_project),
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
    onDeleteRequest: (Project) -> Unit,
    onProjectClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    showSelected: Boolean = false,
    selectedProjectId: Int = 0
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { project -> project.id }
        ) { project ->
            ProjectCard(
                name = project.name,
                highlight = showSelected && selectedProjectId == project.id,
                onClick = { onProjectClick(project.id) },
                onDeleteClick = { onDeleteRequest(project) }
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