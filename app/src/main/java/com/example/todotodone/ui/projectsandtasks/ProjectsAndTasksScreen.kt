package com.example.todotodone.ui.projectsandtasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todotodone.R
import com.example.todotodone.ui.core.StyledFloatingActionButton
import com.example.todotodone.ui.core.TextDialog
import com.example.todotodone.ui.projects.EmptyProjectList
import com.example.todotodone.ui.projects.ProjectList
import com.example.todotodone.ui.tasks.EmptyTaskList
import com.example.todotodone.ui.tasks.TaskList
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProjectsAndTasksScreen(
    modifier: Modifier = Modifier,
    projectsAndTasksViewModel: ProjectsAndTasksViewModel = hiltViewModel()
) {
    val projects by projectsAndTasksViewModel.projects.observeAsState()
    val selectedProjectId by projectsAndTasksViewModel.selectedProjectId.observeAsState()
    val tasks by projectsAndTasksViewModel.tasks.observeAsState()
    var openNewProjectDialog by rememberSaveable { mutableStateOf(false) }
    var openNewTaskDialog by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val undoString = stringResource(id = R.string.undo)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.projects_title)) }) },
        floatingActionButton = {
            if((selectedProjectId ?: 0) != 0){
                StyledFloatingActionButton(
                    icon = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.new_task),
                    onClick = { openNewTaskDialog = true }
                )
            }
        },
        modifier = modifier
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(.3f)
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                if (projects.isNullOrEmpty()) {
                    EmptyProjectList(modifier = Modifier.weight(1f))
                } else {
                    ProjectList(
                        list = projects!!,
                        showSelected = true,
                        selectedProjectId = selectedProjectId ?: 0,
                        onDeleteRequest = {
                            scope.launch {
                                projectsAndTasksViewModel.deleteProject(it)
                                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                                    "Deleted ${it.name}",
                                    actionLabel = undoString
                                )
                                if (snackBarResult == SnackbarResult.ActionPerformed) {
                                    projectsAndTasksViewModel.undoDeleteProject(it)
                                }
                            }
                        },
                        onProjectClick = { projectsAndTasksViewModel.selectProject(it) },
                        modifier = Modifier.weight(1f)
                    )
                }
                Button(
                    onClick = { openNewProjectDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.new_project))
                }
            }
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Column(
                modifier = Modifier.weight(.7f)
            ) {
                when {
                    (selectedProjectId ?: 0) == 0 -> SelectProject()
                    tasks.isNullOrEmpty() -> EmptyTaskList()
                    else -> {
                        TaskList(
                            list = tasks!!,
                            onTaskCompletionChanged = { task, checked ->
                                scope.launch {
                                    projectsAndTasksViewModel.changeTaskCompletion(task, checked)
                                }
                            },
                            onDeleteRequest = {
                                scope.launch {
                                    projectsAndTasksViewModel.deleteTask(it)
                                    val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                                        "Deleted ${it.taskDescription}",
                                        actionLabel = "Undo"
                                    )
                                    if (snackBarResult == SnackbarResult.ActionPerformed) {
                                        projectsAndTasksViewModel.undoDeleteTask(it)
                                    }
                                }
                            },
                            modifier = modifier
                        )
                    }
                }
            }
        }

    }
    if (openNewProjectDialog) {
        TextDialog(
            stringResource(id = R.string.new_project),
            stringResource(id = R.string.project_label),
            stringResource(id = R.string.cancel),
            stringResource(id = R.string.create),
            { openNewProjectDialog = false },
            { newProjectName ->
                projectsAndTasksViewModel.addProject(newProjectName)
                openNewProjectDialog = false
            }
        )
    }
    if (openNewTaskDialog) {
        TextDialog(
            stringResource(id = R.string.new_task),
            stringResource(id = R.string.project_label),
            stringResource(id = R.string.cancel),
            stringResource(id = R.string.create),
            { openNewTaskDialog = false },
            { newTask ->
                projectsAndTasksViewModel.addTask(newTask)
                openNewTaskDialog = false
            }
        )
    }
}