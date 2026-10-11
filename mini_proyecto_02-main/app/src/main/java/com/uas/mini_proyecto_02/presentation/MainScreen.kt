package com.uas.mini_proyecto_02.presentation

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.uas.mini_proyecto_02.components.DeleteCompletedDialog
import com.uas.mini_proyecto_02.components.TaskDialog
import com.uas.mini_proyecto_02.components.TaskItem
import com.uas.mini_proyecto_02.components.TaskTopBar
import com.uas.mini_proyecto_02.data.PreferencesManager
import com.uas.mini_proyecto_02.data.Task
import com.uas.mini_proyecto_02.domain.AppDatabase
import kotlinx.coroutines.launch

@Composable
fun CreateMainScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.taskDao()
    val coroutineScope = rememberCoroutineScope()

    val tasks by dao.getAll().collectAsState(initial = emptyList())

    var filter by remember { mutableStateOf(TaskFilter.ALL) }
    var sort by remember { mutableStateOf(TaskSort.NEWEST) }

    var showTaskDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) } // null = nueva tarea
    var showDeleteCompletedDialog by remember { mutableStateOf(false) }

    val visibleTasks = applySort(applyFilter(tasks, filter), sort)
    val pendingCount = tasks.count { !it.isCompleted }
    val completedCount = tasks.count { it.isCompleted }

    // Soporte para modo oscuro
    val preferencesManager = remember { PreferencesManager(context) }
    var darkTheme by remember { mutableStateOf(preferencesManager.getDarkTheme()) }
    val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()

    // Cambiar los iconos de la barra de estado para que coincidan con el tema actual
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            topBar = {
                TaskTopBar(
                    filter = filter,
                    sort = sort,
                    darkTheme = darkTheme,
                    onFilterChange = { filter = it },
                    onSortChange = { sort = it },
                    onToggleDarkTheme = {
                        val newValue = !darkTheme
                        darkTheme = newValue
                        preferencesManager.saveDarkTheme(newValue)
                    },
                    onDeleteCompleted = {
                        if (completedCount == 0) {
                            Toast.makeText(context, "No hay tareas completadas", Toast.LENGTH_SHORT).show()
                        } else {
                            showDeleteCompletedDialog = true
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        taskToEdit = null
                        showTaskDialog = true
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agegar tarea")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Pendientes: $pendingCount | Completadas: $completedCount",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Filtro: ${filter.label} | Orden: ${sort.label}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (visibleTasks.isEmpty()) {
                    Text(
                        text = if (tasks.isEmpty()) "No hay tareas. Toca + para agregar una."
                        else "No hay tareas con este filtro",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(visibleTasks, key = { it.id }) {
                                task -> TaskItem(
                            task = task,
                            onToggle = {
                                coroutineScope.launch {
                                    dao.update(task.copy(isCompleted = !task.isCompleted))
                                }
                            },
                            onEdit = {
                                taskToEdit = task
                                showTaskDialog = true
                            },
                            onDelete = {
                                coroutineScope.launch { dao.delete(task) }
                            }
                        )
                        }
                    }
                }
            }
        }

        if (showTaskDialog) {
            TaskDialog(
                task = taskToEdit,
                onDismiss = { showTaskDialog = false},
                onConfirm = {
                        title, description ->

                    val editing = taskToEdit

                    coroutineScope.launch {
                        if (editing == null) {
                            dao.insert(Task(title = title, description = description))
                        } else {
                            dao.update(editing.copy(title = title, description = description))
                        }
                    }

                    showTaskDialog = false
                }
            )
        }

        if (showDeleteCompletedDialog) {
            DeleteCompletedDialog(
                completedCount = completedCount,
                onDismiss = { showDeleteCompletedDialog = false },
                onConfirm = {
                    coroutineScope.launch { dao.deleteCompleted() }
                    showDeleteCompletedDialog = false
                }
            )
        }
    }
}