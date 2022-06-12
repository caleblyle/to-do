package com.example.todotodone.ui.core

import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todotodone.R

@Composable
fun ConfirmationDialog(
    title: String,
    body: String,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelClick,
        title = { Text(title) },
        text = { Text(body) },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray),
                onClick = onCancelClick
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick =onConfirmClick) {
                Text(stringResource(R.string.confirm))
            }
        }
    )
}