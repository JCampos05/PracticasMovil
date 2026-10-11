package com.uas.mini_proyecto_02.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.uas.mini_proyecto_02.presentation.TaskFilter
import com.uas.mini_proyecto_02.presentation.TaskSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    filter: TaskFilter,
    sort: TaskSort,
    darkTheme: Boolean,
    onFilterChange: (TaskFilter) -> Unit,
    onSortChange: (TaskSort) -> Unit,
    onToggleDarkTheme: () -> Unit,
    onDeleteCompleted: () -> Unit
) {
    var filterMenuExpanded by remember { mutableStateOf(false) }
    var sortMenuExpanded by remember { mutableStateOf(false) }
    var overflowMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Mis Tareas") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        actions = {
            // Filtrado
            IconButton(
                onClick = {
                    filterMenuExpanded = true
                }
            ) {
                Icon(Icons.Default.FilterList, contentDescription = "Filtrar")
            }

            DropdownMenu(
                expanded = filterMenuExpanded,
                onDismissRequest = { filterMenuExpanded = false }
            ) {
                TaskFilter.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.label,
                                fontWeight = if (option == filter) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onFilterChange(option)
                            filterMenuExpanded = false
                        }
                    )
                }
            }

            // Ordenar
            IconButton(
                onClick = { sortMenuExpanded = true }
            ) {
                Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Ordenar")
            }

            DropdownMenu(
                expanded = sortMenuExpanded,
                onDismissRequest = { sortMenuExpanded = false }
            ) {
                TaskSort.entries.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.label,
                                fontWeight = if (option == sort) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        onClick = {
                            onSortChange(option)
                            sortMenuExpanded = false
                        }
                    )
                }
            }

            // Otras opciones
            IconButton(
                onClick = { overflowMenuExpanded = true }
            ) {
                Icon(Icons.Default.MoreVert, contentDescription = "Mas opciones")
            }

            DropdownMenu(
                expanded = overflowMenuExpanded,
                onDismissRequest = { overflowMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Borrar completadas")
                    },
                    onClick = {
                        overflowMenuExpanded = false
                        onDeleteCompleted()
                    }
                )

                // Modo oscuro
                DropdownMenuItem(
                    text = {
                        Text("Modo oscuro")
                    },
                    trailingIcon = {
                        Checkbox(
                            checked = darkTheme,
                            onCheckedChange = null
                        )
                    },
                    onClick = {
                        overflowMenuExpanded = false
                        onToggleDarkTheme()
                    }
                )
            }
        }
    )
}
