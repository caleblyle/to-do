package com.example.todotodone.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TextInputDialog(
    header: String,
    label: String,
    cancelText: String,
    confirmText: String,
    onCancelClick: () -> Unit,
    onConfirmClick: (String) -> Unit,
) {

    //TODO: Fix styling
    //TODO: Get the focus on the text edit and the keyboard showing immediately
    val focusRequester = remember { FocusRequester() }
    var newItemText by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = {}) {
        Box(
            //TODO: Find a better way to fill the given space on smaller device
            modifier = Modifier
                .widthIn(300.dp, 420.dp)
                .heightIn(64.dp, 200.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            Column {
                Text(header)
                OutlinedTextField(
                    modifier = Modifier
                        .clickable { focusRequester.requestFocus() }
                        .focusRequester(focusRequester)
                        .focusable(),
                    value = newItemText,
                    onValueChange = { newItemText = it },
                    label = { Text(label) }
                )
                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        TextButton(
                            modifier = Modifier.align(alignment = Alignment.End),
                            onClick = onCancelClick
                        ) {
                            Text(cancelText)
                        }
                    }
                    TextButton(onClick = { onConfirmClick(newItemText) }) {
                        Text(confirmText)
                    }
                }
            }
        }
    }
}