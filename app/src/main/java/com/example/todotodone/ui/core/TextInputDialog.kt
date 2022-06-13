package com.example.todotodone.ui.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun TextDialog(
    header: String,
    label: String,
    cancelText: String,
    confirmText: String,
    onCancelClick: () -> Unit,
    onConfirmClick: (String) -> Unit
) {

    var newItemText by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalTextInputService.current

    AlertDialog(
        onDismissRequest = onCancelClick,
        title = { Text(header) },
        properties = DialogProperties(dismissOnClickOutside = false),
        text = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                value = newItemText,
                onValueChange = { newItemText = it },
                label = { Text(label) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onConfirmClick(newItemText) }
                )
            )
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray),
                onClick = onCancelClick
            ) {
                Text(cancelText)
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmClick(newItemText) }) {
                Text(confirmText)
            }
        }
    )

    //Automatically focus on the text field and open the keyboard
    LaunchedEffect(Unit) {
        delay(200)
        focusRequester.requestFocus()
        keyboard?.showSoftwareKeyboard()
    }

}