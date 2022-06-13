package com.example.todotodone.ui.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todotodone.R

@Composable
fun ProjectCard(
    name: String,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    var openMenu by remember { mutableStateOf(false) }

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
        }
    }
}