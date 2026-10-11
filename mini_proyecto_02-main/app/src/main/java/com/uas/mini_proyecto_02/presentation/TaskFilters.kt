package com.uas.mini_proyecto_02.presentation

import com.uas.mini_proyecto_02.data.Task

enum class TaskFilter(val label: String) {
    ALL("Todas"),
    PENDING("Pendientes"),
    COMPLETED("Completadas")
}

enum class TaskSort(val label: String) {
    NEWEST("Fecha: mas recientes"),
    OLDEST("Fecha: mas antiguas"),
    STATUS("Estado: pendientes primero"),
    TITLE("Titulo: A-Z")
}

fun applyFilter(tasks: List<Task>, filter: TaskFilter): List<Task> = when (filter) {
    TaskFilter.ALL -> tasks
    TaskFilter.PENDING -> tasks.filter { !it.isCompleted }
    TaskFilter.COMPLETED -> tasks.filter { it.isCompleted }
}

fun applySort(tasks: List<Task>, sort: TaskSort): List<Task> = when (sort) {
    TaskSort.NEWEST -> tasks.sortedByDescending { it.createdAt }
    TaskSort.OLDEST -> tasks.sortedBy { it.createdAt }
    // false < true
    TaskSort.STATUS -> tasks.sortedWith(
        compareBy<Task> { it.isCompleted }.thenByDescending { it.createdAt }
    )

    TaskSort.TITLE -> tasks.sortedBy { it.title.lowercase() }
}