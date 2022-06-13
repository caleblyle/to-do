package com.example.todotodone.ui.tasks

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todotodone.data.entities.Task

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