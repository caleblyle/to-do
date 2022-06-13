package com.example.todotodone.ui.tasks

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todotodone.R
import com.example.todotodone.data.entities.Task
import com.example.todotodone.ui.core.ConfirmationDialog
import com.example.todotodone.ui.core.StyledFloatingActionButton
import com.example.todotodone.ui.core.TextDialog
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskListScreen(
    projectId: Int,
    modifier: Modifier = Modifier,
    taskListViewModel: TaskListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onDeleteProject: () -> Unit
) {
    val tasks by taskListViewModel.tasks.observeAsState()
    val projectName by taskListViewModel.projectName.observeAsState()
    var openCreateDialog by rememberSaveable { mutableStateOf(false) }
    var confirmProjectDelete by rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        projectName ?: stringResource(id = R.string.unknown),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { confirmProjectDelete = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete_project)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            StyledFloatingActionButton(
                icon = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.new_task),
                onClick = { openCreateDialog = true }
            )
        },
        modifier = modifier
    ) {
        if (tasks.isNullOrEmpty()) {
            EmptyTaskList()
        } else {
            TaskList(
                list = tasks!!,
                onTaskCompletionChanged = { task, checked ->
                    scope.launch {
                        taskListViewModel.changeTaskCompletion(task, checked)
                    }
                },
                onDeleteRequest = {
                    scope.launch {
                        taskListViewModel.deleteTask(it)
                        val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                            "Deleted ${it.taskDescription}",
                            actionLabel = "Undo"
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            taskListViewModel.undoDeleteTask(it)
                        }
                    }
                },
                modifier = modifier
            )
        }
    }
    if (openCreateDialog) {
        TextDialog(
            stringResource(id = R.string.new_task),
            stringResource(id = R.string.project_label),
            stringResource(id = R.string.cancel),
            stringResource(id = R.string.create),
            { openCreateDialog = false },
            { newTask ->
                taskListViewModel.addTask(newTask)
                openCreateDialog = false
            }
        )
    }
    if (confirmProjectDelete) {
        ConfirmationDialog(
            title = stringResource(id = R.string.confirm_project_delete_title),
            body = stringResource(id = R.string.confirm_project_delete_body),
            onCancelClick = { confirmProjectDelete = false },
            onConfirmClick = {
                taskListViewModel.deleteProject()
                onDeleteProject()
            }
        )
    }
}

@Composable
fun TaskList(
    list: List<Task>,
    onTaskCompletionChanged: (Task, Boolean) -> Unit,
    onDeleteRequest: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = list,
            key = { task -> task.id }
        ) { task ->
            TaskItem(
                task = task,
                checked = task.isComplete,
                onCheckedChange = { checked -> onTaskCompletionChanged(task, checked) },
                onDeleteClick = { onDeleteRequest(it) }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    var openMenu by remember { mutableStateOf(false) }

    Row(modifier = Modifier.padding(8.dp)) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked -> onCheckedChange(checked) }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(task.taskDescription)
        }
        Column {
            IconButton(onClick = { openMenu = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.options)
                )
            }
            if(openMenu) {
                DropdownMenu(
                    expanded = openMenu,
                    onDismissRequest = { openMenu = false}
                ) {
                    DropdownMenuItem(
                        onClick = {
                            onDeleteClick(task)
                            openMenu = false
                        }
                    )
                    {
                        Text(stringResource(id = R.string.delete))
                    }
                }
            }
        }
    }

}