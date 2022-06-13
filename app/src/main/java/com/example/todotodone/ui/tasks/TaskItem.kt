package com.example.todotodone.ui.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todotodone.R
import com.example.todotodone.data.entities.Task

@Composable
fun TaskItem(
    task: Task,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        Column {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked -> onCheckedChange(checked) }
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .align(CenterVertically)
                .padding(8.dp)
        ) {
            Text(task.taskDescription)
        }
        Column {
            TaskItemContextMenu(
                onDeleteClick = {
                    onDeleteClick(task)
                }
            )
        }
    }
}

@Composable
fun TaskItemContextMenu(onDeleteClick: () -> Unit) {
    var openMenu by remember { mutableStateOf(false) }

    IconButton(onClick = { openMenu = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.options)
        )
    }
    if (openMenu) {
        DropdownMenu(
            expanded = openMenu,
            onDismissRequest = { openMenu = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    onDeleteClick()
                    openMenu = false
                }
            )
            {
                Text(stringResource(id = R.string.delete))
            }
        }
    }
}